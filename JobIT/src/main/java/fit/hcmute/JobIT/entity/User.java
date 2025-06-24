package fit.hcmute.JobIT.entity;

import fit.hcmute.JobIT.enums.EGender;
import fit.hcmute.JobIT.util.SecurityUtil;
import fit.hcmute.JobIT.util.annotation.enumvalidate.subnet.EnumSubset;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Username is required")
    private String password;
    private int age;

    @Enumerated(EnumType.STRING)
    @EnumSubset(enumClass = EGender.class, anyOf = {"MALE", "FEMALE"})
    private EGender gender;
    private String address;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : null;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : null;
    }
}
