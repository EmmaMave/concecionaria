package com.example.concecionaria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserResponse {

    @JsonProperty("id")
    @Schema(description = "ID del usuario", example = "3")
    private Integer id;

    @JsonProperty("user_name")
    @Schema(description = "Nombre de usuario", example = "nuevo_usuario")
    private String userName;

    @JsonProperty("roles")
    @Schema(description = "Lista de roles asignados")
    private Set<String> roles;
}
