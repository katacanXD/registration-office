package ru.katacan.registrationoffice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "t_work_slots")
@Data
@NoArgsConstructor
public class WorkSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_work_slots_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "f_doctor_id", nullable = false)
    private User doctor;

    @Column(name = "f_date", nullable = false)
    private LocalDate date;

    @Column(name = "f_start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "f_end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "f_slot_minutes", nullable = false)
    private Integer slotMinutes;

    @Column(name = "f_break_start")
    private LocalTime breakStart;

    @Column(name = "f_break_end")
    private LocalTime breakEnd;
}