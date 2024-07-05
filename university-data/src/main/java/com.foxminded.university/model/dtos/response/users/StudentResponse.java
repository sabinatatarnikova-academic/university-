package com.foxminded.university.model.dtos.response.users;

import com.foxminded.university.model.dtos.request.GroupDTO;
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
public class StudentResponse extends UserResponse {

    private GroupDTO group;
}
