package com.foxminded.university.model.entity;

import com.foxminded.university.model.entity.classes.Schedule;
import com.foxminded.university.model.entity.classes.plainclasses.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "groups")
@Data
@ToString(exclude = {"students", "studyClasses", "schedule"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "group_name")
    private String name;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Student> students;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<StudyClass> studyClasses;

    @OneToOne(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Schedule schedule;

    public Group(String name, List<Student> students, List<StudyClass> classes) {
        this.name = name;
        this.students = students;
        this.studyClasses = classes;
    }
}
