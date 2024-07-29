package com.foxminded.university.model.entity.classes.plainClasses;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("ONLINE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OnlineClass extends StudyClass {

    @Column(name = "url")
    private String url;
}
