package ru.katacan.registrationoffice.repository;

import ru.katacan.registrationoffice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByFullName(String fullName);

    Optional<User> findByFullNameAndPassword(String fullName, String password);

    User findByPolicyNumber(String policyNumber);

    List<User> findByAclName(String aclName);

    @Query("SELECT u FROM User u WHERE u.acl.name = 'patient'")
    List<User> findAllPatients();

    @Query("SELECT u FROM User u WHERE u.acl.name IN ('doctor') " +
            "AND (LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.speciality) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<User> searchDoctors(@Param("search") String search);

    @Query("SELECT u FROM User u WHERE u.acl.name IN ('doctor')")
    List<User> findAllDoctors();
}