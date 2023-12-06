package com.projekt.VirtualBetting.model.domains;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true)
    private Long userid;

    @Column(nullable = false, length = 255)
    private String username;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 255)
    private String firstname;

    @Column(nullable = false, length = 255)
    private String lastname;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 255)
    private String country;

    @Column(nullable = false, length = 255)
    private String city;

    @Column(updatable = false ,nullable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private Boolean isActive;

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false
            , orphanRemoval = true
    )
    private Wallet wallet;

    @OneToMany(
            targetEntity = BettingSlip.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
            , orphanRemoval = true
    )
    @Builder.Default
    private List<BettingSlip> bettingSlips = new ArrayList<>();

    @PrePersist
    protected void prePersist() {

        if (createAt == null) {
            createAt = LocalDateTime.now();
        }
        if (isActive == null) {
            isActive = true;
        }
    }

    @PostPersist
    public void initializeWallet() {
        if (wallet == null) {
            Wallet newWallet = new Wallet();
            newWallet.setUser(this);
            this.setWallet(newWallet);
        }
    }
}

