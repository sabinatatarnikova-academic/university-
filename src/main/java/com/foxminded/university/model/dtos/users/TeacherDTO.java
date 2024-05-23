package com.foxminded.university.model.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String rawPassword;
    private Set<String> studyClassesIds;
}
