package com.foxminded.university.repository;

import com.foxminded.university.model.users.Student;
import com.foxminded.university.model.users.Teacher;
import com.foxminded.university.model.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE TYPE(u) = Student")
    Page<Student> findAllStudents(Pageable pageable);

    @Query("SELECT u FROM User u WHERE TYPE(u) = Teacher ")
    Page<Teacher> findAllTeachers(Pageable pageable);
}
