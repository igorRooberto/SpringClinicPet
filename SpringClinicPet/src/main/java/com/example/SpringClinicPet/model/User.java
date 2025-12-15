package com.example.SpringClinicPet.model;

import com.example.SpringClinicPet.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "tb_users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",nullable = false,unique = true)
    private UUID id;

    @Column(name = "login",nullable = false,unique = true, length = 255)
    private String login;

    @Column(name = "password",nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false,length = 20)
    private UserRole role;

    @Column(name = "enabled",nullable = false)
    private Boolean enabled;

    @Column(name = "crvm",unique = true, length = 20)
    private String crvm;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private Set<Pet> pets = new HashSet<>();

    @OneToMany(mappedBy = "veterinarian",fetch = FetchType.LAZY)
    private Set<Appointment> appointmentsVeterinarian = new HashSet<>();

    public User(String login, String password,String email, UserRole role,Boolean enabled,String crvm) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.enabled = enabled;
        this.crvm = crvm;
    }

    public void activate(){
        this.enabled = true;
    }

    public void deactivate(){
        this.enabled = false;
    }

    public void addPet(Pet pet){
        pets.add(pet);
    }

    public void addAppointment(Appointment appointment){
        appointmentsVeterinarian.add(appointment);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN")
                            , new SimpleGrantedAuthority("ROLE_VETERINARIAN")
                            , new SimpleGrantedAuthority("ROLE_USER"));
        }

        else if (this.role == UserRole.VETERINARIAN) {
            return List.of(new SimpleGrantedAuthority("ROLE_VETERINARIAN"), new SimpleGrantedAuthority("ROLE_USER"));
        }

        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
