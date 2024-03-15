package palmfit.PalmFit.payloads.exceptions;

public record ProfileDTO(
        String name,
        String lastname,
        int age,
        String email,
        String avatar
) {
}
