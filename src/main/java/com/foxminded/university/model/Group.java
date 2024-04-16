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
@Table(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Group {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "group_name")
    private String groupName;

    @OneToMany(mappedBy = "group")
    private List<User> users;

    @OneToMany(mappedBy = "group")
    private List<StudyClass> studyClasses;

    public Group(String groupName, List<User> users, List<StudyClass> classes) {
        this.groupName = groupName;
        this.users = users;
        this.studyClasses = classes;
    }
}
