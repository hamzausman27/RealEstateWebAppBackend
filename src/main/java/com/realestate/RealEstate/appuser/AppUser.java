package com.realestate.RealEstate.appuser;

import com.realestate.RealEstate.utils.SearchOption;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "sequence_name",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;

    private String fullName;
    private String email;
    private String phoneNumber;

    private String companyName;
    private String country;

    private String state;
    private String city;

    private String area;

    private String password;

    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    @Column(columnDefinition = "TINYINT")
    private Boolean locked = false;
    @Column(columnDefinition = "TINYINT")
    private Boolean verified = false;


    @Enumerated(EnumType.STRING)
    private SearchOption searchOption;





    public AppUser(String fullName, String email, String phoneNumber, String companyName, String country, String state, String city, String area, String password, AppUserRole appUserRole) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.country = country;
        this.state = state;
        this.city = city;
        this.area = area;
        this.password = password;
        this.appUserRole = appUserRole;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return verified;
    }
}
