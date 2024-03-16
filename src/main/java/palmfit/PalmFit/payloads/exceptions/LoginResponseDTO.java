package palmfit.PalmFit.payloads.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import palmfit.PalmFit.enums.Role;

public record LoginResponseDTO(@JsonProperty("token")String token) {
}
