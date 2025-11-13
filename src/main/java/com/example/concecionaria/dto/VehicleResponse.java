package com.example.concecionaria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VehicleResponse {
    @JsonProperty("id")
    @Schema(description = "Unique identifier for the vehicle", example = "1")
    private Integer id;

    @JsonProperty("brand")
    @Schema(description = "Vehicle brand/make", example = "Nissan")
    private String brand;

    @JsonProperty("model")
    @Schema(description = "Vehicle model", example = "Versa")
    private String model;

    @JsonProperty("year")
    @Schema(description = "Vehicle manufacturing year", example = "2024")
    private Integer year;

    @JsonProperty("vin")
    @Schema(description = "Unique Vehicle Identification Number (VIN)", example = "1N4AL3AP6KC813459")
    private String vin;

    @JsonProperty("engineType")
    @Schema(description = "Type of engine", example = "1.6L 4-Cil")
    private String engineType;

    @JsonProperty("versions")
    @Schema(description = "List of available versions for this vehicle")
    private List<VehicleVersionResponse> versions;

    @JsonProperty("photos")
    @Schema(description = "List of photos for this vehicle")
    private List<VehiclePhotoResponse> photos;

    @JsonProperty("availableColors")
    @Schema(description = "List of available colors for this vehicle")
    private List<AvailableColorResponse> availableColors;
}
