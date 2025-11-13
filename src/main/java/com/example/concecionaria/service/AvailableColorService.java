package com.example.concecionaria.service;

import com.example.concecionaria.dto.AvailableColorRequest;
import com.example.concecionaria.dto.AvailableColorResponse;
import com.example.concecionaria.dto.VehicleResponse;
import com.example.concecionaria.exception.ResourceNotFoundException;
import com.example.concecionaria.model.AvailableColor;
import com.example.concecionaria.model.Vehicle;
import com.example.concecionaria.repository.AvailableColorRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AvailableColorService {

    @Autowired
    private AvailableColorRepository colorRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ModelMapper modelMapper;

    private AvailableColorResponse convertToResponse(AvailableColor color) {
        return modelMapper.map(color, AvailableColorResponse.class);
    }

    public List<AvailableColorResponse> getAllColorsForVehicle(Integer vehicleId) {
        VehicleResponse vehicle = vehicleService.getById(vehicleId);
        return vehicle.getAvailableColors();
    }

    public AvailableColorResponse addColorToVehicle(Integer vehicleId, AvailableColorRequest colorRequest) {
        Vehicle vehicle = vehicleService.findVehicleById(vehicleId);

        AvailableColor color = modelMapper.map(colorRequest, AvailableColor.class);
        vehicle.addAvailableColor(color);

        AvailableColor savedColor = colorRepository.save(color);
        return convertToResponse(savedColor);
    }

    public void deleteColor(Integer colorId) {
        if (!colorRepository.existsById(colorId)) {
            throw new ResourceNotFoundException("AvailableColor", "id", colorId);
        }
        colorRepository.deleteById(colorId);
    }
}
