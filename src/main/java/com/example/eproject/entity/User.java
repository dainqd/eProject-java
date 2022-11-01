package com.example.eproject.entity;

import com.example.eproject.entity.basic.BasicEntity;
import com.example.eproject.util.Enums;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition="text")
    private String avt;

    private String firstName;

    private String lastName;

    private String username;

    @Email(message = "Incorrect email format!, Please re-enter")
    private String email;

    private String phoneNumber ;

    private Date birthday;

    private String gender;

    private String address ;

    @NotNull(message = "Password cannot be left blank")
    @Size(min = 6, message = "password must be greater than or equal to 6")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Enums.AccountStatus status;

    public User(String avt, String firstName, String lastName, String username, String email, Date birthday, String gender, String password){
        this.avt=avt;
        this.firstName=firstName;
        this.lastName=lastName;
        this.username=username;
        this.email=email;
//        this.phoneNumber=phoneNumber;
        this.birthday=birthday;
        this.gender=gender;
//        this.address=address;
        this.password=password;
    }
}