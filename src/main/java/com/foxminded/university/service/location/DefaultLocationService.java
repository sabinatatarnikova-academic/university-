package com.foxminded.university.service.location;

import com.foxminded.university.model.Location;
import com.foxminded.university.model.classes.OfflineClass;
import com.foxminded.university.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class DefaultLocationService implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public void saveLocation(String department, String classroom, List<OfflineClass> classes) {
        log.info("Adding new location: department - {}, classroom - {}, classes - {}", department, classroom, classes);
        Location location = new Location(department, classroom, classes);
        locationRepository.save(location);
        log.info("Saved location: department - {}, classroom - {}, classes - {}", department, classroom, classes);
    }

    @Override
    public Location findLocationById(String locationId) {
        log.info("Searching for location with id {}", locationId);
        Location location = locationRepository.findById(locationId).get();
        log.info("Founded the location with id {}", locationId);
        return location;
    }

    @Override
    public Location findLocationByDepartmentAndClassroom(String department, String classroom) {
        log.info("Searching for location with department - {} and classroom - {}", department, classroom);
        Location location = locationRepository.findLocationByDepartmentAndClassroom(department, classroom).get();
        log.info("Founded the location with department - {} and classroom - {}", department, classroom);
        return location;
    }

    @Override
    public void updateLocation(String locationId, String department, String classroom, List<OfflineClass> classes) {
        log.info("Updating location with id {}, department - {}, classroom - {}, classes - {}", locationId, department, classroom, classes);
        Location location = new Location(locationId, department, classroom, classes);
        locationRepository.save(location);
        log.info("Updated location with id {}, department - {}, classroom - {}, classes - {}", locationId, department, classroom, classes);
    }

    @Override
    public void deleteLocationById(String locationId) {
        log.info("Deleting location with id {}", locationId);
        locationRepository.deleteById(locationId);
        log.info("Deleted location with id - {}", locationId);
    }

    @Override
    public List<Location> findAllLocationsWithPagination(int pageNumber, int pageSize) {
        log.info("Searching for location with page size {} and pageSize {}", pageNumber, pageSize);
        Page<Location> pageResult = locationRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} locations", pageResult.getTotalPages());
        return pageResult.toList();
    }
}
