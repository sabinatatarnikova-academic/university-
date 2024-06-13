package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.classes.StudyClass;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface StudyClassMapper {

    StudyClassDTO toDto(StudyClass studyClass);

    @InheritInverseConfiguration
    @BeanMapping(builder = @Builder(disableBuilder = true))
    @SubclassMapping(source = OnlineClassDTO.class, target = OnlineClass.class)
    @SubclassMapping(source = OfflineClassDTO.class, target = OfflineClass.class)
    StudyClass toEntity(StudyClassDTO studyClassDTO);
}
