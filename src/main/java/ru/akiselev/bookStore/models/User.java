package ru.akiselev.bookStore.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.akiselev.bookStore.enums.Role;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_info")
public class User {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "user_info_id_seq", sequenceName = "user_info_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_info_id_seq")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "generated_url")
    private String generatedUrl;

    @Column(name = "email_sent")
    private Boolean emailSentFlag;
}
