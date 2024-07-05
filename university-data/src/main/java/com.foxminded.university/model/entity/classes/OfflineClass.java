package com.foxminded.university.model.entity.classes;

import com.foxminded.university.model.entity.Location;
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
@DiscriminatorValue("OFFLINE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OfflineClass extends StudyClass {

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
