package com.example.concecionaria.controller;


import com.example.concecionaria.dto.VehicleRequest;
import com.example.concecionaria.dto.VehicleResponse;
import com.example.concecionaria.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicles")
@Tag(name="Vehicles",description = "Provides methods for managing vehicles")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @Operation(summary = "Get all vehicles")
    @ApiResponse(responseCode = "200", description = "Vehicles found", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VehicleResponse.class))) })
    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get a vehicle by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle found",
                    content = @Content(schema = @Schema(implementation = VehicleResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found") })
    @GetMapping("/{idVehicle}")
    public ResponseEntity<VehicleResponse> getById(@PathVariable Integer idVehicle) {
        return ResponseEntity.ok(service.getById(idVehicle));
    }

    @Operation(summary = "Get all vehicles with pagination")
    @ApiResponse(responseCode = "200", description = "Vehicles found (paginated)")
    @GetMapping(value = "/pagination")
    public ResponseEntity<List<VehicleResponse>> getAllPaginated(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        return ResponseEntity.ok(service.getAll(page, pageSize));
    }

    @Operation(summary = "Register a new used vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Used vehicle registered successfully",
                    content = @Content(schema = @Schema(implementation = VehicleResponse.class))),
            @ApiResponse(responseCode = "409", description = "VIN already exists")
    })
    @PostMapping
    public ResponseEntity<VehicleResponse> create(@Valid @RequestBody VehicleRequest vehicleRequest) {
        VehicleResponse savedVehicle = service.save(vehicleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
    }

    @Operation(summary = "Update an existing vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle updated",
                    content = @Content(schema = @Schema(implementation = VehicleResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found") })
    @PutMapping("/{idVehicle}")
    public ResponseEntity<VehicleResponse> update(@PathVariable Integer idVehicle,
                                                  @Valid @RequestBody VehicleRequest vehicleRequest) {
        VehicleResponse updatedVehicle = service.update(idVehicle, vehicleRequest);
        return ResponseEntity.ok(updatedVehicle);
    }

//    @Operation(summary = "Delete a vehicle")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "Vehicle deleted"),
//            @ApiResponse(responseCode = "404", description = "Vehicle not found") })
//    @DeleteMapping("/{idVehicle}")
//    public ResponseEntity<Void> delete(@PathVariable Integer idVehicle) {
//        service.delete(idVehicle);
//        return ResponseEntity.noContent().build();
//    }

    @Operation(summary = "Get vehicles by brand")
    @ApiResponse(responseCode = "200", description = "Vehicles found")
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<VehicleResponse>> getByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(service.getByBrand(brand));
    }

    @Operation(summary = "Get vehicles by model")
    @ApiResponse(responseCode = "200", description = "Vehicles found")
    @GetMapping("/model/{model}")
    public ResponseEntity<List<VehicleResponse>> getByModel(@PathVariable String model) {
        return ResponseEntity.ok(service.getByModel(model));
    }

    @Operation(summary = "Get a vehicle by its VIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle found",
                    content = @Content(schema = @Schema(implementation = VehicleResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found") })
    @GetMapping("/vin/{vin}")
    public ResponseEntity<VehicleResponse> getByVin(@PathVariable String vin) {
        return ResponseEntity.ok(service.getByVin(vin));
    }

}
