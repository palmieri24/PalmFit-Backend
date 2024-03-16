package palmfit.PalmFit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import palmfit.PalmFit.entities.User;
import palmfit.PalmFit.payloads.exceptions.LoginResponseDTO;
import palmfit.PalmFit.payloads.exceptions.UserDTO;
import palmfit.PalmFit.payloads.exceptions.UserLoginDTO;
import palmfit.PalmFit.services.AuthService;
import palmfit.PalmFit.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody UserLoginDTO body){
        return authService.authenticateUserAndGenerateToken(body);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody UserDTO body){
        return this.authService.save(body);
    }

}

