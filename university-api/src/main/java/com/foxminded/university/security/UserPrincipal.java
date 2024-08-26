package com.foxminded.university.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements Principal {

    private String id;
    private String name;

    @Override
    public String getName() {
        return name;
    }
}