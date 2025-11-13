package com.example.concecionaria.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="availablecolors")
public class AvailableColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_color")
    @JsonProperty("id_Color")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicle", nullable = false)
    @JsonBackReference("vehicle_color")
    private Vehicle vehicle;

    @Column(name = "color_name", nullable = false, length = 100)
    @JsonProperty("colorName")
    private String colorName;

}
