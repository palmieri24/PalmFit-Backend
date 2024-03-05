package palmfit.PalmFit.payloads.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorPayloadWithList extends ErrorPayload{
    List<String> errorsList;
    public ErrorPayloadWithList(String message, LocalDateTime date, List<String> errorsList) {
        super(message, date);
        this.errorsList = errorsList;
    }
}