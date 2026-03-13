package ru.katacan.registrationoffice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_users")
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_users_id")
    private Long id;

    @Column(name = "f_fio", nullable = false)
    private String fullName;

    @Column(name = "f_password", nullable = false)
    private String password;

    @Column(name = "f_policy_number", length = 30)
    private String policyNumber;

    @ManyToOne
    @JoinColumn(name = "f_acl_name_id", nullable = false)
    private DictAclName acl;

    @Column(name = "f_speciality_name")
    private String speciality;
}