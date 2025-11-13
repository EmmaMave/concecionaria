package com.example.concecionaria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailableColorResponse {
    @JsonProperty("id")
    @Schema(description = "Unique identifier for the color", example = "17")
    private Integer id;

    @JsonProperty("colorName")
    @Schema(description = "Name of the available color", example = "Rojo Perlado")
    private String colorName;
}
