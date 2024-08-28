package com.foxminded.university.model.entity.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "token_id", unique = true)
    private String id;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "created_time")
    private LocalDateTime createdAt;

    @Column(name = "expires_time")
    private LocalDateTime expiresAt;
}
