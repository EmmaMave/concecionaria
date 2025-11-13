package com.example.concecionaria.service;


import com.example.concecionaria.dto.VehicleResponse;
import com.example.concecionaria.dto.VehicleVersionRequest;
import com.example.concecionaria.dto.VehicleVersionResponse;
import com.example.concecionaria.exception.ResourceNotFoundException;
import com.example.concecionaria.model.Vehicle;

import com.example.concecionaria.model.VehicleVersion;
import com.example.concecionaria.repository.VehicleRepository;
import com.example.concecionaria.repository.VehicleVersionRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleVersionService {

    @Autowired
    private VehicleVersionRepository versionRepository;

    @Autowired
    private VehicleService vehicleService; // Para encontrar el vehículo padre

    @Autowired
    private ModelMapper modelMapper;

    private VehicleVersionResponse convertToResponse(VehicleVersion version) {
        return modelMapper.map(version, VehicleVersionResponse.class);
    }

    public List<VehicleVersionResponse> getAllVersionsForVehicle(Integer vehicleId) {
        VehicleResponse vehicle = vehicleService.getById(vehicleId);
        return vehicle.getVersions();
    }

    public VehicleVersionResponse addVersionToVehicle(Integer vehicleId, VehicleVersionRequest versionRequest) {
        Vehicle vehicle = vehicleService.findVehicleById(vehicleId);

        VehicleVersion version = modelMapper.map(versionRequest, VehicleVersion.class);

        vehicle.addVersion(version);

        VehicleVersion savedVersion = versionRepository.save(version);
        return convertToResponse(savedVersion);
    }

    public void deleteVersion(Integer versionId) {
        if (!versionRepository.existsById(versionId)) {
            throw new ResourceNotFoundException("VehicleVersion", "id", versionId);
        }
        versionRepository.deleteById(versionId);
    }
}
