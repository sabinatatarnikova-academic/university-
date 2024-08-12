package com.foxminded.university.service.classes;

import com.foxminded.university.model.dtos.request.schedule.GlobalStudyClassRequest;
import com.foxminded.university.model.entity.classes.GlobalStudyClass;

import java.util.List;

public interface GlobalStudyClassesService {

    void parseScheduleListToGlobalClasses(List<GlobalStudyClassRequest> globalClasses);

    GlobalStudyClass parseGlobalClassToStudyClasses(GlobalStudyClassRequest globalStudyClassRequest);

    GlobalStudyClass findGlobalClassById(String id);

    void deleteGlobalClass(String id);
}
