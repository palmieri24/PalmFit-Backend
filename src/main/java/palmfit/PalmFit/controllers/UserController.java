package palmfit.PalmFit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import palmfit.PalmFit.entities.User;
import palmfit.PalmFit.exceptions.BadRequestException;
import palmfit.PalmFit.payloads.exceptions.ProfileDTO;
import palmfit.PalmFit.payloads.exceptions.UserDTO;
import palmfit.PalmFit.payloads.exceptions.UserResponseDTO;
import palmfit.PalmFit.payloads.exceptions.UserUpdateInfoDTO;
import palmfit.PalmFit.payloads.exceptions.*;
import palmfit.PalmFit.services.AuthService;
import palmfit.PalmFit.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String orderBy){
        return userService.getUsers(page, size, orderBy);
    }

    @GetMapping("/myProfile")
    @ResponseStatus(HttpStatus.OK)
    public User findById(@AuthenticationPrincipal User user){
        return userService.findById(user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(@RequestBody @Validated UserDTO body, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Payload Error!");
        }else {
            User newUser = authService.save(body);
            return new UserResponseDTO(newUser.getId());
        }
    }

    @PutMapping("/{userId}")
    public User findAndUpdate(@PathVariable UUID userId, @RequestBody UserUpdateInfoDTO body){
        return authService.update(userId,body);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable UUID userId){
        userService.findByIdAndDelete(userId);
    }


//PROFILE
    @GetMapping("/me")
    public ProfileDTO getProfile(@AuthenticationPrincipal User currentUser){
        return userService.getProfile(currentUser);
    }

    @PutMapping("/updateMe")
    public User updateProfile(@AuthenticationPrincipal User currentUser, @RequestBody UserUpdateInfoDTO updatedUser){
        return this.authService.update(currentUser.getId(), updatedUser);

    @GetMapping("/myMemb")
    public ProfileMembershipDTO getProfileMembership(@AuthenticationPrincipal User currentUser){
        return userService.getProfileMembership(currentUser);
    }


    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getMeAndDelete(@AuthenticationPrincipal User currentUser){
        this.userService.findByIdAndDelete(currentUser.getId());
    }

    @PostMapping("/uploadAvatar")
    public String uploadAvatar(@PathVariable UUID id, @RequestParam("avatar") MultipartFile img) throws IOException {
        return this.userService.uploadImg(img, id);
    }
}
