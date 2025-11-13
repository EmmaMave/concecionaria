package com.example.concecionaria.repository;

import com.example.concecionaria.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {
    Optional<Vehicle> findById(Integer id);
    Optional<Vehicle> findByVin(String vin);
    List<Vehicle> findByBrand(String brand);
    List<Vehicle> findByModel(String model);
}

