package com.example.concecionaria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleRequest {
    @JsonProperty("brand")
    @NotBlank(message = "Brand is required")
    @Schema(description = "Vehicle brand/make", example = "Nissan")
    private String brand;

    @JsonProperty("model")
    @NotBlank(message = "Model is required")
    @Schema(description = "Vehicle model", example = "Versa")
    private String model;

    @JsonProperty("year")
    @NotNull(message = "Year is required")
    @Schema(description = "Vehicle manufacturing year", example = "2024")
    private Integer year;

    @JsonProperty("vin")
    @NotBlank(message = "VIN is required")
    @Size(max = 17)
    @Schema(description = "Unique Vehicle Identification Number (VIN)", example = "1N4AL3AP6KC813459")
    private String vin;

    @JsonProperty("engineType")
    @Size(max = 50)
    @Schema(description = "Type of engine", example = "1.6L 4-Cil")
    private String engineType;
}
