package com.foxminded.university.model.dtos.response;

import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupEditResponse {

    private GroupRequest group;
    private Page<StudentResponse> students;
    private List<StudyClassResponse> studyClasses;
}
