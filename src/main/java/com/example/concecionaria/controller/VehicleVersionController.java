package com.example.concecionaria.controller;

import com.example.concecionaria.dto.VehicleVersionRequest;
import com.example.concecionaria.dto.VehicleVersionResponse;
import com.example.concecionaria.service.VehicleVersionService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("api/v1/versions")
@Tag(name = "Versions", description = "Provides methods for managing vehicle versions")
public class VehicleVersionController {

    @Autowired
    private VehicleVersionService versionService;

    @Operation(summary = "Get all versions for a vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Versions found"),
            @ApiResponse(responseCode = "404", description = "Parent vehicle not found")
    })
    @GetMapping
    public ResponseEntity<List<VehicleVersionResponse>> getAllVersions(@PathVariable Integer vehicleId) {
        return ResponseEntity.ok(versionService.getAllVersionsForVehicle(vehicleId));
    }

    @Operation(summary = "Add a new version to a vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Version added successfully"),
            @ApiResponse(responseCode = "404", description = "Parent vehicle not found")
    })
    @PostMapping
    public ResponseEntity<VehicleVersionResponse> addVersion(@PathVariable Integer vehicleId,
                                                             @Valid @RequestBody VehicleVersionRequest request) {
        VehicleVersionResponse savedVersion = versionService.addVersionToVehicle(vehicleId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVersion);
    }

//    @Operation(summary = "Delete a version by its ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "Version deleted"),
//            @ApiResponse(responseCode = "404", description = "Version not found")
//    })
//    @DeleteMapping("/{versionId}")
//    public ResponseEntity<Void> deleteVersion(@PathVariable Integer vehicleId,
//                                              @PathVariable Integer versionId) {
//        versionService.deleteVersion(versionId);
//        return ResponseEntity.noContent().build();
//    }
}
