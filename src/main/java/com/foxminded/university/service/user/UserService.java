package com.foxminded.university.service.user;

import com.foxminded.university.model.dtos.users.UserDTO;
import com.foxminded.university.model.dtos.users.UserFormDTO;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.utils.RequestPage;
import org.springframework.data.domain.Page;


public interface UserService {

    void saveUser(UserDTO userDTO);

    <U extends User> U findUserById(String userId);

    void updateStudent(UserFormDTO userFormDTO);

    void updateTeacher(UserFormDTO userFormDTO);

    void deleteUserById(String userId);

    Page<User> findAllUsersWithPagination(RequestPage requestPage);

    Page<Student> findAllStudentsWithPagination(RequestPage requestPage);

    Page<Teacher> findAllTeachersWithPagination(RequestPage requestPage);
}
