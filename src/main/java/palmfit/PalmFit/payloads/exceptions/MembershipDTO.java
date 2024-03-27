package palmfit.PalmFit.payloads.exceptions;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import palmfit.PalmFit.entities.User;
import palmfit.PalmFit.enums.MembershipType;

import java.time.LocalDate;
import java.util.UUID;

public record MembershipDTO(
        @Enumerated(EnumType.STRING)
        MembershipType membershipType
) {
}
