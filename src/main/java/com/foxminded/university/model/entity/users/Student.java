package com.foxminded.university.model.entity.users;

import com.foxminded.university.model.entity.Group;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("STUDENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Student extends User{

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
