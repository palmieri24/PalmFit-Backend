package palmfit.PalmFit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import palmfit.PalmFit.enums.Role;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"password", "credentialsNonExpired", "accountNonExpired", "authorities", "accountNonLocked", "enabled"})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String lastname;
    private int age;
    private String email;
    private String password;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "user")
    private Membership membership;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.name + " " + this.lastname;
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
        return true;
    }
}
