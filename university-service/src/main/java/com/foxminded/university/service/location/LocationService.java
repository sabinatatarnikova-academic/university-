package com.foxminded.university.service.location;

import com.foxminded.university.model.dtos.request.LocationDTO;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.utils.RequestPage;

import java.util.List;

public interface LocationService {

    void saveLocation(Location location);

    Location findLocationById(String locationId);

    Location findLocationByDepartmentAndClassroom(String department, String classroom);

    void updateLocation(Location location);

    void deleteLocationById(String locationId);

    List<Location> findAllLocationsWithPagination(RequestPage pageRequest);

    List<LocationDTO> findAllLocations();
}
