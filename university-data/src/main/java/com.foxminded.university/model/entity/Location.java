package com.foxminded.university.model.entity;

import com.foxminded.university.model.entity.classes.plainclasses.OfflineClass;
import jakarta.persistence.CascadeType;
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
    private String department;

    @Column(name = "classroom", length = 50)
    private String classroom;

    @OneToMany(mappedBy = "location", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OfflineClass> studyClass;

    public Location(String department, String classroom, List<OfflineClass> classes) {
        this.classroom = classroom;
        this.department = department;
        this.studyClass = classes;
    }
}
