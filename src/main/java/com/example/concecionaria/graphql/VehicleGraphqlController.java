package com.example.concecionaria.graphql;

import com.example.concecionaria.dto.*;
import com.example.concecionaria.service.AvailableColorService;
import com.example.concecionaria.service.VehiclePhotoService;
import com.example.concecionaria.service.VehicleService;
import com.example.concecionaria.service.VehicleVersionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VehicleGraphqlController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleVersionService versionService;

    @Autowired
    private VehiclePhotoService photoService;

    @Autowired
    private AvailableColorService colorService;

    @QueryMapping
    public List<VehicleResponse> allVehicles() {
        return vehicleService.getAll();
    }

    @QueryMapping
    public VehicleResponse vehicleById(@Argument Integer id) {
        return vehicleService.getById(id);
    }

    @QueryMapping
    public VehicleResponse vehicleByVin(@Argument String vin) {
        return vehicleService.getByVin(vin);
    }

    @QueryMapping
    public List<VehicleResponse> vehiclesByBrand(@Argument String brand) {
        return vehicleService.getByBrand(brand);
    }

    @QueryMapping
    public List<VehicleResponse> vehiclesByModel(@Argument String model) {
        return vehicleService.getByModel(model);
    }

    @MutationMapping
    public VehicleResponse createVehicle(@Argument @Valid VehicleRequest request) {
        return vehicleService.save(request);
    }

    @MutationMapping
    public VehicleResponse updateVehicle(@Argument Integer id, @Argument @Valid VehicleRequest request) {
        return vehicleService.update(id, request);
    }


    @MutationMapping
    public VehicleVersionResponse addVersionToVehicle(@Argument Integer vehicleId, @Argument @Valid VehicleVersionRequest request) {
        return versionService.addVersionToVehicle(vehicleId, request);
    }


    @MutationMapping
    public VehiclePhotoResponse addPhotoToVehicle(@Argument Integer vehicleId, @Argument @Valid VehiclePhotoRequest request) {
        return photoService.addPhotoToVehicle(vehicleId, request);
    }


    @MutationMapping
    public AvailableColorResponse addColorToVehicle(@Argument Integer vehicleId, @Argument @Valid AvailableColorRequest request) {
        return colorService.addColorToVehicle(vehicleId, request);
    }
}
