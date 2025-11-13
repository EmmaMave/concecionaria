package com.example.concecionaria.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {
    // Patrones de validación
    private static final Pattern VIN_PATTERN = Pattern.compile("^[A-Z0-9]{17}$");
    private static final Pattern STATE_PATTERN = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");

    /*
     * ===========================
     * Métodos de Validación
     * ===========================
     */

    /**
     * Valida que el VIN tenga exactamente 17 caracteres (letras mayúsculas o números)
     */
    public static void validateVin(String vin) {
        if (vin == null || vin.trim().isEmpty()) {
            throw new IllegalArgumentException("El VIN no puede estar vacío");
        }

        if (!VIN_PATTERN.matcher(vin).matches()) {
            throw new IllegalArgumentException(
                    "El VIN '" + vin + "' debe tener exactamente 17 caracteres (solo letras mayúsculas y números)"
            );
        }
    }

    /**
     * Valida que el año no sea en el futuro
     */
    public static void validateYear(Integer year) {
        if (year == null) {
            throw new IllegalArgumentException("El año no puede ser nulo");
        }

        int currentYear = java.time.Year.now().getValue();
        if (year > currentYear) {
            throw new IllegalArgumentException("El año " + year + " no puede ser en el futuro");
        }

        // Validación adicional: año mínimo razonable
        if (year < 1900) {
            throw new IllegalArgumentException("El año no puede ser menor a 1900");
        }
    }

    /**
     * Valida que el estado solo contenga letras
     */
    public static void validateState(String state) {
        if (state == null || state.trim().isEmpty()) {
            return; // Estado opcional, si es null o vacío no hay problema
        }

        if (!STATE_PATTERN.matcher(state).matches()) {
            throw new IllegalArgumentException("El estado '" + state + "' solo puede contener letras");
        }
    }

    /**
     * Valida todos los campos de un vehículo
     */
    public static void validateVehicle(String vin, Integer year, String state) {
        validateVin(vin);
        validateYear(year);
        validateState(state);
    }

    /**
     * Valida todos los campos de un vehículo usado
     */
    public static void validateUsedVehicle(String vin, Integer year, String state, Integer mileage, Double price) {
        validateVehicle(vin, year, state);

        if (mileage != null && mileage < 0) {
            throw new IllegalArgumentException("El kilometraje no puede ser negativo");
        }

        if (price != null && price < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
    }

    /*
     * ===========================
     * Manejo de Excepciones
     * ===========================
     */

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(EntityNotFoundException ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleConflict(DataIntegrityViolationException ex) {
        String message = ex.getMessage();

        // Detectar específicamente violación de unicidad de VIN
        if (message != null && message.contains("vin") && message.contains("unique")) {
            return createErrorResponse(HttpStatus.CONFLICT, "DUPLICATE_VIN",
                    "El VIN ya existe en el sistema");
        }

        // Detectar otras violaciones de integridad
        if (message != null && message.contains("constraint") && message.contains("violat")) {
            return createErrorResponse(HttpStatus.CONFLICT, "DATA_INTEGRITY_VIOLATION",
                    "Violación de restricción de base de datos");
        }

        return createErrorResponse(HttpStatus.CONFLICT, "DATA_INTEGRITY_VIOLATION",
                "Violación de integridad de datos");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleIllegalArgument(IllegalArgumentException ex) {
        String message = ex.getMessage();
        String code = "VALIDATION_ERROR";

        // Códigos específicos basados en el mensaje
        if (message != null) {
            if (message.contains("VIN")) {
                code = "INVALID_VIN";
            } else if (message.contains("año") || message.contains("año")) {
                code = "INVALID_YEAR";
            } else if (message.contains("estado")) {
                code = "INVALID_STATE";
            } else if (message.contains("kilometraje")) {
                code = "INVALID_MILEAGE";
            } else if (message.contains("precio")) {
                code = "INVALID_PRICE";
            } else if (message.contains("paginación") || message.contains("page") || message.contains("pageSize")) {
                code = "INVALID_PAGINATION";
            }
        }

        return createErrorResponse(HttpStatus.BAD_REQUEST, code, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = createErrorResponse(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                "Error de validación en los datos de entrada"
        );

        // Agregar detalles de campos con errores
        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null ?
                                fieldError.getDefaultMessage() : "Error de validación"
                ));

        errorResponse.put("fieldErrors", fieldErrors);
        return errorResponse;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleNotReadable(HttpMessageNotReadableException ex) {
        String message = "JSON mal formado o tipos de datos incorrectos";
        if (ex.getMostSpecificCause() != null) {
            String specificMessage = ex.getMostSpecificCause().getMessage();
            if (specificMessage.contains("java.time") || specificMessage.contains("LocalDate")) {
                message = "Formato de fecha inválido. Use formato YYYY-MM-DD";
            } else if (specificMessage.contains("Integer") || specificMessage.contains("int")) {
                message = "Valor numérico inválido";
            } else if (specificMessage.contains("Double") || specificMessage.contains("double")) {
                message = "Valor decimal inválido";
            } else {
                message = specificMessage;
            }
        }

        return createErrorResponse(HttpStatus.BAD_REQUEST, "MALFORMED_JSON", message);
    }

    /*
     * ===========================
     * Excepción genérica para errores inesperados
     * ===========================
     */

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleGenericException(Exception ex) {
        // En producción, no exponer detalles internos
        String message = "Error interno del servidor";

        // En desarrollo, puedes mostrar más detalles
        if (isDevelopment()) {
            message = ex.getMessage();
        }

        return createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                message
        );
    }

    /*
     * ===========================
     * Métodos auxiliares
     * ===========================
     */

    private Map<String, Object> createErrorResponse(HttpStatus status, String code, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", Instant.now().toString());
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("code", code);
        errorResponse.put("message", message);
        return errorResponse;
    }

    private boolean isDevelopment() {
        // Puedes implementar lógica para detectar el entorno
        // Por ahora, asumimos desarrollo si no estamos en producción
        return true; // Cambiar a false en producción
    }

    //____________________________________________________________________________________
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        Map<String, String> body = Map.of(
                "message", ex.getMessage(),
                "path", request.getDescription(false)
        );
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<Map<String, String>> handleResourceConflictException(ResourceConflictException ex, WebRequest request) {
        Map<String, String> body = Map.of(
                "message", ex.getMessage(),
                "path", request.getDescription(false)
        );
        return new ResponseEntity<>(body, HttpStatus.CONFLICT); // 409
    }

    // --- 2. 👈 AÑADE ESTE MÉTODO COMPLETO ---
    /**
     * Captura las excepciones de credenciales incorrectas (ej. login fallido)
     * y las convierte de un 500 (Error Interno) a un 401 (No Autorizado).
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        Map<String, String> body = Map.of(
                "message", "Credenciales incorrectas. Por favor, verifique su usuario y contraseña.",
                "error", "Unauthorized"
        );
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED); // 401
    }
}
