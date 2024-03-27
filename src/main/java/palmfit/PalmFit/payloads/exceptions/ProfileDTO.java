package palmfit.PalmFit.payloads.exceptions;

import palmfit.PalmFit.entities.Membership;
import palmfit.PalmFit.enums.MembershipType;

import java.time.LocalDate;

public record ProfileDTO(
        String name,
        String lastname,
        int age,
        String email,
        String avatar,
        Membership membership
) {
}
