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

    List<User> findByRoleName(String roleName);

    @Query("SELECT u FROM User u WHERE u.role.name = 'patient'")
    List<User> findAllPatients();

    @Query("SELECT u FROM User u " +
            "WHERE (:roleName IS NULL OR u.role.name = :roleName) " +
            "AND (LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.speciality) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<User> searchByRoleAndText(@Param("search") String search, @Param("roleName") String roleName);

    default List<User> searchDoctors(String search) {
        return searchByRoleAndText(search, "doctor");
    }

    default List<User> searchPatients(String search) {
        return searchByRoleAndText(search, "patient");
    }

    default List<User> searchUsers(String search) {
        return searchByRoleAndText(search, null);
    }

    @Query("SELECT u FROM User u WHERE u.role.name IN ('doctor')")
    List<User> findAllDoctors();
}