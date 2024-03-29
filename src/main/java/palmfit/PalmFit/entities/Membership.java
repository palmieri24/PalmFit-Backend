package palmfit.PalmFit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import palmfit.PalmFit.enums.MembershipType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "memberships")
public class Membership {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;
    private int price;
    private String description;
    private LocalDate start_date;
    private LocalDate exp_date;
    @OneToMany(mappedBy = "membership")
    @JsonManagedReference
    private Set<User> users = new HashSet<>();
}
