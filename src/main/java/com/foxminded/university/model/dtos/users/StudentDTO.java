package com.foxminded.university.model.dtos.users;

import com.foxminded.university.model.dtos.GroupDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class StudentDTO extends UserDTO {

    private GroupDTO group;
}
