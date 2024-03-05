package palmfit.PalmFit.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import palmfit.PalmFit.entities.User;
import palmfit.PalmFit.exceptions.NotFoundException;
import palmfit.PalmFit.payloads.exceptions.UserDTO;
import palmfit.PalmFit.repositories.UserDAO;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    Cloudinary cloudinaryUploader;


    public User findById(UUID id) {
        return userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public User findByEmail(String email){
        return userDAO.findByEmail(email).orElseThrow( () -> new NotFoundException("Email " + email + " not found!"));
    }

    public List<User> getUsers(){
        return userDAO.findAll();
    }

    public List<User> getUsersById(List<UUID> usersIds){
        return userDAO.findAllById(usersIds);
    }

    public void findByIdAndDelete(UUID id){
        User found = this.findById(id);
        userDAO.delete(found);
    }

    public User findByIdAndUpdate(UUID id, UserDTO body){
        User found = this.findById(id);
        found.setName(body.name());
        found.setLastname(body.lastname());
        found.setAge(body.age());
        found.setEmail(body.email());
        found.setPassword(body.password());
        found.setAvatar(body.avatar());
        found.setRole(body.role());
        return userDAO.save(found);
    }

    public String uploadImg(MultipartFile img, UUID id) throws IOException {
        User found = findById(id);
        String url = (String) cloudinaryUploader.uploader().upload(img.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(url);
        userDAO.save(found);
        return url;
    }
}
