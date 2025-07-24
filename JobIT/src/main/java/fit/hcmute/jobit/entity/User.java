package fit.hcmute.jobit.entity;

import fit.hcmute.jobit.enums.EGender;
import fit.hcmute.jobit.util.annotation.enumvalidate.subnet.EnumSubset;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User extends AbstractEntity{

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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Resume> resumes;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
