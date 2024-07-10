package com.foxminded.university.model.dtos.request.users;

import com.foxminded.university.model.dtos.request.GroupFormation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFormRequest {

    private String id;

    private String firstName;

    private String lastName;

    @ToString.Exclude
    private String username;

    @ToString.Exclude
    private String password;

    private String userType;

    private GroupFormation group;

    private List<String> studyClassesIds;
}

