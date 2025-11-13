package com.example.concecionaria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @JsonProperty("username")
    @NotBlank
    @Size(min = 3, max = 100)
    @Schema(description = "Nombre de usuario para el login", example = "nuevo_usuario")
    private String userName;

    @JsonProperty("password")
    @NotBlank
    @Size(min = 6)
    @Schema(description = "Contraseña (se cifrará automáticamente)", example = "nueva123")
    private String password;

}
