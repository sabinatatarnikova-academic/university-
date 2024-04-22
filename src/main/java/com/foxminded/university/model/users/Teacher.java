package com.foxminded.university.model.users;

import com.foxminded.university.model.classes.StudyClass;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("teacher")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends User{
    @OneToMany(mappedBy = "teacher")
    private List<StudyClass> studyClasses;
}
