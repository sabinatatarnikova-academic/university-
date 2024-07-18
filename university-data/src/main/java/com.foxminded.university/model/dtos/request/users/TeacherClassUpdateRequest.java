package com.foxminded.university.model.dtos.request.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherClassUpdateRequest {

    private String id;
    private List<String> studyClassesIds;
}
