package com.foxminded.university.model.dtos.classes;

import com.foxminded.university.model.dtos.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OfflineClassDTO extends StudyClassDTO {

    private LocationDTO location;
}
