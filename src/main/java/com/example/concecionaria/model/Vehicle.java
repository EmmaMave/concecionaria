package com.example.concecionaria.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table (name="vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id_vehicle")
    @JsonProperty("id_Vehiculo")
    private Integer id;

    @Column(name = "brand", nullable = false, length = 100)
    @JsonProperty("brand")
    private String brand;

    @Column(name = "model", nullable = false, length = 100)
    @JsonProperty("modelo")
    private String model;

    @Column(name = "year", nullable = false)
    @JsonProperty("anio")
    private Integer year;

    @Column(name = "vin", unique = true, nullable = false, length = 20)
    @JsonProperty("vin")
    private String vin;

    @Column(name = "engine_type", length = 50)
    @JsonProperty("tipoMotor")
    private String engineType;

    //  RELACIÓN 1:N con VehicleVersion
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("vehicle-version")
    private List<VehicleVersion> versions = new ArrayList<>();

    //  RELACIÓN 1:N con VehiclePhoto
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("vehicle-photo")
    private List<VehiclePhoto> photos = new ArrayList<>();

    //  RELACIÓN 1:N con AvailableColor
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("vehicle-color")
    private List<AvailableColor> availableColors = new ArrayList<>();

    public Vehicle() {}

    public Vehicle(String brand, String model, Integer year, String vin, String engineType) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.vin = vin;
        this.engineType = engineType;
    }

    public void addVersion(VehicleVersion version) {
        versions.add(version);
        version.setVehicle(this);
    }

    public void addPhoto(VehiclePhoto photo) {
        photos.add(photo);
        photo.setVehicle(this);
    }

    public void addAvailableColor(AvailableColor color) {
        availableColors.add(color);
        color.setVehicle(this);
    }
}
