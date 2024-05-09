package com.leo.ourproperty.repository;

import com.leo.ourproperty.entity.User;
import com.leo.ourproperty.repository.projection.PropertyProjection;
import com.leo.ourproperty.repository.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select c from User c")
    Page<UserProjection> findAllPageable(Pageable pageable);

    Optional<User> findByEmail(String email);

    @Query("select u.role from User u where u.email like :email")
    User.Role findRoleByEmail(String email);


    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
