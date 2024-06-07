package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.classes.StudyClass;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface StudyClassMapper {

    StudyClassDTO toDto(StudyClass studyClass);

    @InheritInverseConfiguration
    StudyClass toEntity(StudyClassDTO studyClassDTO);

    default StudyClass fromStudyClassDtoToEntity(StudyClassDTO dto) {
        if (dto instanceof OnlineClassDTO) {
            return toOnlineClass((OnlineClassDTO) dto);
        } else if (dto instanceof OfflineClassDTO) {
            return toOfflineClass((OfflineClassDTO) dto);
        } else {
            throw new IllegalArgumentException("Unknown subclass of StudyClassDTO: " + dto.getClass());
        }
    }

    OnlineClass toOnlineClass(OnlineClassDTO dto);

    OfflineClass toOfflineClass(OfflineClassDTO dto);
}
