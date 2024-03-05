package palmfit.PalmFit.payloads.exceptions;

import jakarta.validation.constraints.NotEmpty;
import palmfit.PalmFit.enums.Role;

public record UserDTO(
        @NotEmpty(message = "The name is required.")
        String name,
        @NotEmpty(message = "The lastname is required.")
        String lastname,
        int age,
        @NotEmpty(message = "The email is required.")
        String email,
        @NotEmpty(message = "The password is required.")
        String password,
        String avatar,
        Role role) {
}
