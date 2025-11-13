package com.example.concecionaria.repository;


import com.example.concecionaria.model.VehicleVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleVersionRepository extends JpaRepository<VehicleVersion, Integer> {

}
