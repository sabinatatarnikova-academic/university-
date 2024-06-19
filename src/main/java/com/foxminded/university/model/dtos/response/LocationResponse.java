package com.foxminded.university.model.dtos.response;

import com.foxminded.university.model.dtos.response.classes.OfflineClassResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationResponse {

    private String id;
    private String department;
    private String classroom;
    private List<OfflineClassResponse> offlineClasses;
}
