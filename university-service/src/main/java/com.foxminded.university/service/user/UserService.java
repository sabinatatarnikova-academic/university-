package com.foxminded.university.service.user;

import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.dtos.response.users.UserResponse;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.utils.RequestPage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    void saveUser(UserResponse userResponse);

    <U extends User> U findUserById(String userId);

    UserResponse findUserDTOById(String userId);

    <U extends User> U findUserByUsername(String username);

    void updateStudent(UserFormRequest userFormRequest);

    void updateTeacher(UserFormRequest userFormRequest);

    void deleteUserById(String userId);

    Page<UserResponse> findAllUsersWithPagination(RequestPage requestPage);

    Page<StudentResponse> findAllStudentsWithPagination(RequestPage requestPage);

    Page<TeacherResponse> findAllTeachersWithPagination(RequestPage requestPage);

    List<TeacherResponse> findAllTeachers();

    void updateUser(UserFormRequest userFormRequest);

    Page<CourseDTO> showCoursesThatAssignedToStudent(RequestPage requestPage);
}
