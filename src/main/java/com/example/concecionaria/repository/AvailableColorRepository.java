package com.example.concecionaria.repository;

import com.example.concecionaria.model.AvailableColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableColorRepository extends JpaRepository<AvailableColor, Integer> {

}
