package com.example.concecionaria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="vehiclephotos")
public class VehiclePhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_photo")
    @JsonProperty("id_Photo")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicle", nullable = false)
    @JsonBackReference("vehicle_photo")
    private Vehicle vehicle;

    @Column(name = "image_url", nullable = false, length = 500)
    @JsonProperty("image_Url")
    private String imageUrl;

    @Column(name = "description", length = 200)
    @JsonProperty("description")
    private String description;

}
