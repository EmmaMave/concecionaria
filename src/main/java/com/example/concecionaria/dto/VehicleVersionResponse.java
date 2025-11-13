package com.example.concecionaria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleVersionResponse {
    @JsonProperty("id")
    @Schema(description = "Unique identifier for the version", example = "10")
    private Integer id;

    @JsonProperty("versionName")
    @Schema(description = "Name of the vehicle version", example = "Advance")
    private String versionName;
}
