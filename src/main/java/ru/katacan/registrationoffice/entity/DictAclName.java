package ru.katacan.registrationoffice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_dict_acl_name")
@Data
@NoArgsConstructor
public class DictAclName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "f_acl_name_id")
    private Long id;

    @JsonValue
    @Column(name = "f_acl_name", nullable = false, unique = true, length = 50)
    private String name;
}