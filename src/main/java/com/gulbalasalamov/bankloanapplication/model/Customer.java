package com.gulbalasalamov.bankloanapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
@Entity
public class Customer {
    @Id
    @Column(name = "national_identity_number",length = 11,updatable = false, nullable = false)
    @NotBlank(message = "national identity number can not be blank")
    @Pattern(regexp = "[1-9][0-9]{10}")
    private String nationalIdentityNumber;

    @NotBlank(message = "name can not be null")
    private String name;

    @NotBlank(message = "surname can not be null")
    private String surname;

    @NotNull(message = "monthly income can not be null")
    @Min(1)
    @Column(name = "monthly_income")
    private Double monthlyIncome;

    @NotBlank(message = "phone can not be null")
    private String phone;

    @Email
    private String email;

    @Transient
    private Integer creditScore;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<CreditApplication> creditApplications;

}
