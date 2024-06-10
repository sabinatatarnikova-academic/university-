package com.foxminded.university.service.location;

import com.foxminded.university.model.entity.Location;
import com.foxminded.university.repository.LocationRepository;
import com.foxminded.university.utils.RequestPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public void saveLocation(Location location) {
        locationRepository.save(location);
        log.info("Saved location: department - {}, classroom - {}, classes - {}", location.getDepartment(), location.getClassroom(), location.getStudyClass());
    }

    @Override
    public Location findLocationById(String locationId) {
        Optional<Location> location = locationRepository.findById(locationId);
        if (!location.isPresent()) {
            log.error("Location with id {} not found", locationId);
            throw new NoSuchElementException();
        }
        log.info("Founded the location with id {}", locationId);
        return location.get();
    }

    @Override
    public Location findLocationByDepartmentAndClassroom(String department, String classroom) {
        Location location = locationRepository.findLocationByDepartmentAndClassroom(department, classroom).get();
        log.info("Founded the location with department - {} and classroom - {}", department, classroom);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        locationRepository.save(location);
        log.info("Updated location with id {}, department - {}, classroom - {}, classes - {}", location.getId(),location.getDepartment(), location.getClassroom(), location.getStudyClass());
    }

    @Override
    public void deleteLocationById(String locationId) {
        locationRepository.deleteById(locationId);
        log.info("Deleted location with id - {}", locationId);
    }

    @Override
    public List<Location> findAllLocationsWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Page<Location> pageResult = locationRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} locations", pageResult.getTotalPages());
        return pageResult.toList();
    }
}
