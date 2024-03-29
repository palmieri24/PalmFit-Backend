package palmfit.PalmFit.payloads.exceptions;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import palmfit.PalmFit.enums.MembershipType;

import java.time.LocalDate;

public record ProfileMembershipDTO(
        @Enumerated(EnumType.STRING)
        MembershipType membershipType,
        LocalDate start_date,
        LocalDate exp_date
) {
}
