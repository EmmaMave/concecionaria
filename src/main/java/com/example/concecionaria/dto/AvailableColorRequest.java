package com.example.concecionaria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailableColorRequest {
    @JsonProperty("colorName")
    @NotBlank(message = "Color name is required")
    @Size(max = 50)
    @Schema(description = "Name of the available color", example = "Rojo Perlado")
    private String colorName;
}
