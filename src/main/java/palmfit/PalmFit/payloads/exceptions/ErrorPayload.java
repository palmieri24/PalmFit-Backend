package palmfit.PalmFit.payloads.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorPayload {
    private String message;
    private LocalDateTime timestamp;
}
