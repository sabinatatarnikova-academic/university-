package com.foxminded.university.model.dtos.users;

import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*@ToString(exclude = {"studyClasses"})*/
@Builder
public class UserDTO {

    private String id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    private String password;

    private String repeatedPassword;

    @NotBlank(message = "User type is mandatory")
    private String userType;

    private String groupId;

    private Set<String> studyClassesIds;
}
