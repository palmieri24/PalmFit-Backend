package palmfit.PalmFit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import palmfit.PalmFit.enums.MembershipType;

import java.time.LocalDate;
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
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public void calculateExp(){
        if(start_date == null || membershipType == null){
            throw new IllegalArgumentException("Start date and membership type are required! ");
        }
        switch (membershipType){
            case MONTHLY: exp_date = start_date.plusMonths(1);
            break;
            case THREE_MONTH: exp_date = start_date.plusMonths(3);
            break;
            case ANNUAL: exp_date = start_date.plusYears(1);
            break;
        }
    }

}
