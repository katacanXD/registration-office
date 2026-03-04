package ru.katacan.registrationoffice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_dict_status")
@Data
@NoArgsConstructor
public class DictStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_status_id")
    private Long id;

    @Column(name = "f_status_name", nullable = false, unique = true, length = 50)
    private String name;
}