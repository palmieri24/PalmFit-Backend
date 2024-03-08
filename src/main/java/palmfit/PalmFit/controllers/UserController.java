package palmfit.PalmFit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import palmfit.PalmFit.entities.User;
import palmfit.PalmFit.payloads.exceptions.UserDTO;
import palmfit.PalmFit.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("")
    public List<User> getAllUsers(){
        return this.userService.getUsers();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User findById(@PathVariable UUID userId){
        return userService.findById(userId);
    }

    @PutMapping("/{userId}")
    public User findAndUpdate(@PathVariable UUID userId, @RequestBody UserDTO body){
        return userService.findByIdAndUpdate(userId,body);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable UUID userId){
        userService.findByIdAndDelete(userId);
    }



    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentUser){
        return currentUser;
    }

    @PutMapping("/me")
    public User getMeAndUpdate(@AuthenticationPrincipal User currentUser, @RequestBody UserDTO updatedUser){
        return this.userService.findByIdAndUpdate(currentUser.getId(), updatedUser);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getMeAndDelete(@AuthenticationPrincipal User currentUser){
        this.userService.findByIdAndDelete(currentUser.getId());
    }

    @PostMapping("/upload")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile img, @RequestParam("id") UUID id) throws IOException {
        return this.userService.uploadImg(img, id);
    }
}
