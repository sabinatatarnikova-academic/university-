package com.foxminded.university.model.dtos;

import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDTO {

    private String id;

    private String department;

    private String classroom;

    private List<OfflineClassDTO> studyClass;
}
