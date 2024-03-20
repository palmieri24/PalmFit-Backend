package palmfit.PalmFit.payloads.exceptions;

public record UserUpdateInfoDTO(
        String name,
        String lastname,
        int age,
        String email
) {
}
