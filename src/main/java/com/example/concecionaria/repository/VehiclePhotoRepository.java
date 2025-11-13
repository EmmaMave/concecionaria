package com.example.concecionaria.repository;

import com.example.concecionaria.model.VehiclePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiclePhotoRepository extends JpaRepository<VehiclePhoto, Integer> {

}
