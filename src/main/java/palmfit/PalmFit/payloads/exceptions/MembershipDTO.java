package palmfit.PalmFit.payloads.exceptions;

import palmfit.PalmFit.entities.User;
import palmfit.PalmFit.enums.MembershipType;

import java.time.LocalDate;
import java.util.UUID;

public record MembershipDTO(
        MembershipType membershipType,
        int price,
        LocalDate start_date,
        String description,
        UUID user_id
) {
}
