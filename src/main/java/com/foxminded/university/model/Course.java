package com.foxminded.university.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @Column (name = "course_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "course_name")
    private String name;

    @OneToMany (mappedBy = "course")
    private List<StudyClass> studyStudyClasses;

    public Course(String courseName, List<StudyClass> studyClass) {
        this.name = courseName;
        this.studyStudyClasses = studyClass;
    }
}
