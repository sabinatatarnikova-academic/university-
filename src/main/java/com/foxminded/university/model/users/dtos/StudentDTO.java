package com.foxminded.university.model.users.dtos;

import com.foxminded.university.model.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String rawPassword;
    private Group group;
}
