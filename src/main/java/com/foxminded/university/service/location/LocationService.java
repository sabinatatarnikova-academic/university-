package com.foxminded.university.service.location;

import com.foxminded.university.model.Location;

import java.util.List;

public interface LocationService {

    void saveLocation(Location location);

    Location findLocationById(String locationId);

    Location findLocationByDepartmentAndClassroom(String department, String classroom);

    void updateLocation(Location location);

    void deleteLocationById(String locationId);

    List<Location> findAllLocationsWithPagination(int pageNumber, int pageSize);
}
