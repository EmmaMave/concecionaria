package com.example.concecionaria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleVersionRequest {
    @JsonProperty("versionName")
    @NotBlank(message = "Version name is required")
    @Size(max = 100)
    @Schema(description = "Name of the vehicle version/trim", example = "Advance")
    private String versionName;
}
