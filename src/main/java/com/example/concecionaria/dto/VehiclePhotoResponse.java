package com.example.concecionaria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiclePhotoResponse {
    @JsonProperty("id")
    @Schema(description = "Unique identifier for the photo", example = "25")
    private Integer id;

    @JsonProperty("imageUrl")
    @Schema(description = "Full URL of the vehicle photo", example = "https://example.com/images/versa_rojo.jpg")
    private String imageUrl;

    @JsonProperty("description")
    @Schema(description = "Optional description of the photo", example = "Front view")
    private String description;
}
