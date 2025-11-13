package com.example.concecionaria.service;

import com.example.concecionaria.dto.VehiclePhotoRequest;
import com.example.concecionaria.dto.VehiclePhotoResponse;
import com.example.concecionaria.dto.VehicleResponse;
import com.example.concecionaria.exception.ResourceNotFoundException;
import com.example.concecionaria.model.Vehicle;
import com.example.concecionaria.model.VehiclePhoto;
import com.example.concecionaria.repository.VehiclePhotoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class VehiclePhotoService {
    @Autowired
    private VehiclePhotoRepository photoRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ModelMapper modelMapper;

    private VehiclePhotoResponse convertToResponse(VehiclePhoto photo) {
        return modelMapper.map(photo, VehiclePhotoResponse.class);
    }

    public List<VehiclePhotoResponse> getAllPhotosForVehicle(Integer vehicleId) {
        VehicleResponse vehicle = vehicleService.getById(vehicleId);
        return vehicle.getPhotos();
    }

    public VehiclePhotoResponse addPhotoToVehicle(Integer vehicleId, VehiclePhotoRequest photoRequest) {
        Vehicle vehicle = vehicleService.findVehicleById(vehicleId);

        VehiclePhoto photo = modelMapper.map(photoRequest, VehiclePhoto.class);
        vehicle.addPhoto(photo);

        VehiclePhoto savedPhoto = photoRepository.save(photo);
        return convertToResponse(savedPhoto);
    }

    public void deletePhoto(Integer photoId) {
        if (!photoRepository.existsById(photoId)) {
            throw new ResourceNotFoundException("VehiclePhoto", "id", photoId);
        }
        photoRepository.deleteById(photoId);
    }
}
