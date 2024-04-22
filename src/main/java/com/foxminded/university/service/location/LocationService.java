package com.foxminded.university.service.location;

import com.foxminded.university.model.Location;
import com.foxminded.university.model.classes.OfflineClass;

import java.util.List;

public interface LocationService {

    void saveLocation(String department, String classroom, List<OfflineClass> classes);

    Location findLocationById(String locationId);

    Location findLocationByDepartmentAndClassroom(String department, String classroom);

    void updateLocation(String locationId, String department, String classroom,  List<OfflineClass> classes);

    void deleteLocationById(String locationId);

    List<Location> findAllLocationsWithPagination(int pageNumber, int pageSize);
}
