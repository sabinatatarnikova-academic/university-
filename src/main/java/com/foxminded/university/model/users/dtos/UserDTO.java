package com.foxminded.university.model.users.dtos;

import com.foxminded.university.model.Group;
import com.foxminded.university.model.classes.StudyClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"studyClasses"})
@Builder
public class UserDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String rawPassword;
    private String userType;
    private Group group;
    private List<StudyClass> studyClasses;
}
