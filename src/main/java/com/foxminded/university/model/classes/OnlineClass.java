package com.foxminded.university.model.classes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("online")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineClass extends StudyClass {
    private String url;
}
