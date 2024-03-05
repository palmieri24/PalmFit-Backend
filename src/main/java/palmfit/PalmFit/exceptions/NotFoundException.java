package palmfit.PalmFit.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException{

    public NotFoundException(UUID id){super("Id '" + id + "' not found");}

    public NotFoundException(String message){super(message);}
}

