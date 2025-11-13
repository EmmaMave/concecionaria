package com.example.concecionaria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiclePhotoRequest {
    @JsonProperty("imageUrl")
    @NotBlank(message = "Image URL is required")
    @Schema(description = "Full URL of the vehicle photo", example = "https://example.com/images/versa_rojo.jpg")
    private String imageUrl;

    @JsonProperty("description")
    @Size(max = 200)
    @Schema(description = "Optional description of the photo", example = "Front view")
    private String description;
}
