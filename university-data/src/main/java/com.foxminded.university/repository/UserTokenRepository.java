package com.foxminded.university.repository;

import com.foxminded.university.model.entity.users.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, String> {

    UserToken findByToken(String token);

    void deleteByUserId(String userId);
}
