package palmfit.PalmFit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import palmfit.PalmFit.entities.User;
import palmfit.PalmFit.enums.Role;
import palmfit.PalmFit.exceptions.BadRequestException;
import palmfit.PalmFit.exceptions.UnauthorizedException;
import palmfit.PalmFit.payloads.exceptions.LoginResponseDTO;
import palmfit.PalmFit.payloads.exceptions.UserDTO;
import palmfit.PalmFit.payloads.exceptions.UserLoginDTO;
import palmfit.PalmFit.payloads.exceptions.UserUpdateInfoDTO;
import palmfit.PalmFit.repositories.UserDAO;
import palmfit.PalmFit.security.JWTTools;

import java.util.UUID;

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
        userDAO.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("Email" + user.getEmail() + "is already in use!");
        });
        User newUser = new User();
        newUser.setName(body.name());
        newUser.setLastname(body.lastname());
        newUser.setAge(body.age());
        newUser.setEmail(body.email());
        newUser.setPassword(bcrypt.encode(body.password()));
        newUser.setAvatar("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.lastname());
        newUser.setRole(Role.USER);
        return userDAO.save(newUser);
    }

        public LoginResponseDTO authenticateUserAndGenerateToken(UserLoginDTO payload) {
        User user = userService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            String token = jwtTools.createToken(user);
            return new LoginResponseDTO(token);
        } else {
            throw new UnauthorizedException("Credenziali sbagliate!");
        }
    }

    public User update(UUID id, UserUpdateInfoDTO body){
        User found = userService.findById(id);
        if (body.name() != null) {
            found.setName(body.name());
        }
        if (body.lastname() != null) {
            found.setLastname(body.lastname());
        }
       found.setAge(body.age());
        if (body.email() != null){
            found.setEmail(body.email());
        }
        return userDAO.save(found);
    }

}

