package com.example.concecionaria.controller;

import com.example.concecionaria.dto.AvailableColorRequest;
import com.example.concecionaria.dto.AvailableColorResponse;
import com.example.concecionaria.service.AvailableColorService;

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
@RequestMapping("api/v1/colors")
@Tag(name = "Available Colors", description = "Provides methods for managing available vehicle colors")
public class AvailableColorController {

    @Autowired
    private AvailableColorService colorService;

    @Operation(summary = "Get all available colors for a vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colors found"),
            @ApiResponse(responseCode = "404", description = "Parent vehicle not found")
    })
    @GetMapping
    public ResponseEntity<List<AvailableColorResponse>> getAllColors(@PathVariable Integer vehicleId) {
        return ResponseEntity.ok(colorService.getAllColorsForVehicle(vehicleId));
    }

    @Operation(summary = "Add a new color to a vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Color added successfully"),
            @ApiResponse(responseCode = "404", description = "Parent vehicle not found")
    })
    @PostMapping
    public ResponseEntity<AvailableColorResponse> addColor(@PathVariable Integer vehicleId,
                                                           @Valid @RequestBody AvailableColorRequest request) {
        AvailableColorResponse savedColor = colorService.addColorToVehicle(vehicleId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedColor);
    }

//    @Operation(summary = "Delete a color by its ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "Color deleted"),
//            @ApiResponse(responseCode = "404", description = "Color not found")
//    })
//    @DeleteMapping("/{colorId}")
//    public ResponseEntity<Void> deleteColor(@PathVariable Integer vehicleId,
//                                            @PathVariable Integer colorId) {
//        colorService.deleteColor(colorId);
//        return ResponseEntity.noContent().build();
//    }
}
