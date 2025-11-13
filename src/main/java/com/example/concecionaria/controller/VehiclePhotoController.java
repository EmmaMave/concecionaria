package com.example.concecionaria.controller;

import com.example.concecionaria.dto.VehiclePhotoRequest;
import com.example.concecionaria.dto.VehiclePhotoResponse;
import com.example.concecionaria.service.VehiclePhotoService;
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
@RequestMapping("api/v1/photos")
@Tag(name = "Vehicle Photos", description = "Provides methods for managing vehicle photos")
public class VehiclePhotoController {

    @Autowired
    private VehiclePhotoService photoService;

    @Operation(summary = "Get all photos for a vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Photos found"),
            @ApiResponse(responseCode = "404", description = "Parent vehicle not found")
    })
    @GetMapping
    public ResponseEntity<List<VehiclePhotoResponse>> getAllPhotos(@PathVariable Integer vehicleId) {
        return ResponseEntity.ok(photoService.getAllPhotosForVehicle(vehicleId));
    }

    @Operation(summary = "Add a new photo to a vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Photo added successfully"),
            @ApiResponse(responseCode = "404", description = "Parent vehicle not found")
    })
    @PostMapping
    public ResponseEntity<VehiclePhotoResponse> addPhoto(@PathVariable Integer vehicleId,
                                                         @Valid @RequestBody VehiclePhotoRequest request) {
        VehiclePhotoResponse savedPhoto = photoService.addPhotoToVehicle(vehicleId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPhoto);
    }

//    @Operation(summary = "Delete a photo by its ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "Photo deleted"),
//            @ApiResponse(responseCode = "404", description = "Photo not found")
//    })
//    @DeleteMapping("/{photoId}")
//    public ResponseEntity<Void> deletePhoto(@PathVariable Integer vehicleId,
//                                            @PathVariable Integer photoId) {
//        photoService.deletePhoto(photoId);
//        return ResponseEntity.noContent().build();
//    }
}
