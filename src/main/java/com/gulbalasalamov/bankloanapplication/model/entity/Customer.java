package com.gulbalasalamov.bankloanapplication.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
//import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
@Entity

public class Customer {
    @Id
    @Column(name = "national_identity_number",length = 11,updatable = false, nullable = false)
    @NotBlank(message = "national identity number can not be blank")
    @Pattern(regexp = "[1-9][0-9]{10}")
//    @Column(name = "national_identity_number")
    private String nationalIdentityNumber;

    @NotBlank(message = "name can not be null")
    private String firstName;

    @NotBlank(message = "surname can not be null")
    private String lastName;

    @NotNull(message = "monthly income can not be null")
    @Min(1)
    private Double monthlyIncome;

    private String gender;

    @Min(18)
    private  Integer age;

    @NotBlank(message = "phone can not be null")
    private String phone;

    @Email
    private String email;

    private Integer loanScore = 500;

//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<LoanApplication> loanApplications;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_national_identity_number")
    private List<LoanApplication> loanApplications;

}
