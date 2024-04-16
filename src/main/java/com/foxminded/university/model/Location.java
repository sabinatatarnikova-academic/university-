package com.foxminded.university.model;

import com.foxminded.university.model.enums.Classroom;
import com.foxminded.university.model.enums.Department;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Id
    @Column(name = "location_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "department", length = 50)
    private Department department;

    @Column(name = "classroom", length = 50)
    private Classroom classroom;

    @OneToMany(mappedBy = "location")
    private List<StudyClass> studyClass;

    public Location(Department department, Classroom classroom, List<StudyClass> classes) {
        this.classroom = classroom;
        this.department = department;
        this.studyClass = classes;
    }
}
