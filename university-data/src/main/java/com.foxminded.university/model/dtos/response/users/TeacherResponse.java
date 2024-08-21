package com.foxminded.university.model.dtos.response.users;

import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TeacherResponse extends UserDTO {

    private List<StudyClassResponse> studyClasses;
}
