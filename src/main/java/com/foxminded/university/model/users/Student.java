package com.foxminded.university.model.users;

import com.foxminded.university.model.Group;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student extends User{

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

}
