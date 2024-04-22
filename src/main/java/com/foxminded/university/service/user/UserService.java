package com.foxminded.university.service.user;

import com.foxminded.university.model.Group;
import com.foxminded.university.model.classes.StudyClass;
import com.foxminded.university.model.users.User;

import java.util.List;

public interface UserService {

    void saveStudent(String firstName, String lastName, Group group);

    void saveTeacher(String firstName, String lastName, List<StudyClass> classes);

    User findUserById(String userId);

    void updateStudent(String userId, String firstName, String lastName, Group group);

    void updateTeacher(String userId, String firstName, String lastName,List<StudyClass> classes);

    void deleteUserById(String userId);

    List<User> findAllUsersWithPagination(int pageNumber, int pageSize);
}
