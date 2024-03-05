package palmfit.PalmFit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import palmfit.PalmFit.payloads.exceptions.ErrorPayload;
import palmfit.PalmFit.payloads.exceptions.ErrorPayloadWithList;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload handleBadRequest(BadRequestException ex) {
        if (ex.getErrorList() != null) {
            List<String> errorsList = ex.getErrorList().stream().map(objectError -> objectError.getDefaultMessage()).toList();
            return new ErrorPayloadWithList(ex.getMessage(), LocalDateTime.now(), errorsList);
        } else {
            return new ErrorPayload(ex.getMessage(), LocalDateTime.now());
        }

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorPayload handleUnauthorized(UnauthorizedException ex) {
        return new ErrorPayload(ex.getMessage(), LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorPayload handleAccessDenied(AccessDeniedException ex) {
        return new ErrorPayload("You don't have access to this endpoint!", LocalDateTime.now());
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorPayload handleNotFound(NotFoundException ex) {
        return new ErrorPayload(ex.getMessage(), LocalDateTime.now());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorPayload handleGenericErrors(Exception ex) {
        ex.printStackTrace();
        return new ErrorPayload("Server-side problem! We'll fix it soon!", LocalDateTime.now());
    }


}

