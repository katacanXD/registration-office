package ru.katacan.registrationoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.katacan.registrationoffice.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByAclName(String aclName);
    User findByPolicyNumber(String policyNumber);
}