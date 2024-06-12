package com.project.shopapp.repositories;

import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<User,Long> {
 boolean exitstsByPhoneNumber(String phoneNumber);

 Optional<User> findByPhoneNumber(String phoneNumber);
}
