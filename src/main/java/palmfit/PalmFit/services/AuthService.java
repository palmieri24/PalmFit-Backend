package palmfit.PalmFit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import palmfit.PalmFit.entities.User;
import palmfit.PalmFit.exceptions.UnauthorizedException;
import palmfit.PalmFit.payloads.exceptions.UserDTO;
import palmfit.PalmFit.payloads.exceptions.UserLoginDTO;
import palmfit.PalmFit.repositories.UserDAO;
import palmfit.PalmFit.security.JWTTools;

@Service
public class AuthService {

    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder bcrypt;


    public User save(UserDTO body){
        User newUser = new User(body.name(), body.lastname(), body.age(), body.email(), bcrypt.encode(body.password()),
                body.avatar(),body.role());
        return userDAO.save(newUser);
    }

    public String authenticateUserAndGenerateToken(UserLoginDTO payload) {
        User user = userService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali sbagliate!");
        }
    }

}

