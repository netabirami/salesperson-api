package com.salfri.salesperson_api;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sales_person")
public class SalesPersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

}