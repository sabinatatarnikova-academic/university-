package com.foxminded.university.model.dtos.users;

import com.foxminded.university.model.dtos.classes.StudyClassDTO;
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
public class TeacherDTO extends UserDTO {

    private List<StudyClassDTO> studyClasses;
}
