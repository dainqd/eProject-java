package com.example.eproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "location")
public class Location {
    @Id
    private long id;
    private String place = "";
    private String ward = "";
    private String district = "";
    private String city = "";
    private String country = "Viet Nam";
}
