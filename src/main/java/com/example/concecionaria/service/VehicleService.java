package com.example.concecionaria.service;

import com.example.concecionaria.dto.VehicleRequest;
import com.example.concecionaria.dto.VehicleResponse;
import com.example.concecionaria.exception.ResourceNotFoundException;
import com.example.concecionaria.model.Vehicle;
import com.example.concecionaria.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleService {
    @Autowired
    private VehicleRepository repository;

    @Autowired
    private ModelMapper modelMapper;


    private VehicleResponse convertToResponse(Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleResponse.class);
    }

    private Vehicle convertToEntity(VehicleRequest vehicleRequest) {
        return modelMapper.map(vehicleRequest, Vehicle.class);
    }


    public List<VehicleResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<VehicleResponse> getAll(int page, int pageSize) {
        if (page < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Parámetros de paginación inválidos");
        }
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Vehicle> vehicles = repository.findAll(pageReq);

        return vehicles.getContent()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public VehicleResponse getById(Integer idVehicle) {
        Vehicle vehicle = repository.findById(idVehicle)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", idVehicle));
        return convertToResponse(vehicle);
    }

    public VehicleResponse getByVin(String vin) {
        Vehicle vehicle = repository.findByVin(vin)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "vin", vin));
        return convertToResponse(vehicle);
    }

    public List<VehicleResponse> getByBrand(String brand) {
        return repository.findByBrand(brand)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<VehicleResponse> getByModel(String model) {
        return repository.findByModel(model)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public VehicleResponse save(VehicleRequest vehicleRequest) {
        Vehicle vehicle = convertToEntity(vehicleRequest);
        Vehicle savedVehicle = repository.save(vehicle);
        return convertToResponse(savedVehicle);
    }

    public VehicleResponse update(Integer idVehicle, VehicleRequest vehicleRequest) {
        Vehicle existingVehicle = repository.findById(idVehicle)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", idVehicle));

        existingVehicle.setBrand(vehicleRequest.getBrand());
        existingVehicle.setModel(vehicleRequest.getModel());
        existingVehicle.setYear(vehicleRequest.getYear());
        existingVehicle.setVin(vehicleRequest.getVin());
        existingVehicle.setEngineType(vehicleRequest.getEngineType());

        Vehicle updatedVehicle = repository.save(existingVehicle);
        return convertToResponse(updatedVehicle);
    }

    public void delete(Integer idVehicle) {
        if (!repository.existsById(idVehicle)) {
            throw new ResourceNotFoundException("Vehicle", "id", idVehicle);
        }
        repository.deleteById(idVehicle);
    }

    public Vehicle findVehicleById(Integer idVehicle) {
        return repository.findById(idVehicle)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", idVehicle));
    }

}
