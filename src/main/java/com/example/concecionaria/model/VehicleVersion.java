package com.example.concecionaria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="versions")
public class VehicleVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_version")
    @JsonProperty("idVersion")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicle", nullable = false)
    @JsonBackReference("vehicle_version")
    private Vehicle vehicle;

    @Column(name = "version_name", nullable = false, length = 100)
    @JsonProperty("version_Name")
    private String versionName;

    public VehicleVersion() {}

}
