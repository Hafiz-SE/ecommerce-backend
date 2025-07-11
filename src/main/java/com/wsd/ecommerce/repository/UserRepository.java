package com.wsd.ecommerce.repository;

import com.wsd.ecommerce.constant.UserType;
import com.wsd.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Page<User> findAllByUserType(UserType userType, Pageable pageable);
}
