package palmfit.PalmFit.payloads.exceptions;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import palmfit.PalmFit.enums.MembershipType;

import java.util.List;

public record PricingPlanDTO(
        @Enumerated(EnumType.STRING)
        MembershipType membershipType,
        List<String> description,
        int price
) {
}
