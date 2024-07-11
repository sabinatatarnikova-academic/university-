package com.foxminded.university.model.dtos.response.classes;

import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.request.LocationDTO;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditStudyClassResponse {

    private List<CourseDTO> courses;
    private List<GroupFormation> groups;
    private List<TeacherResponse> teachers;
    private List<LocationDTO> locations;
}
