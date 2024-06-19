package com.foxminded.university.model.dtos.response.classes;

import com.foxminded.university.model.dtos.request.LocationDTO;
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
public class OfflineClassResponse extends StudyClassResponse {

    private LocationDTO location;
}
