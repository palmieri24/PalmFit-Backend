package palmfit.PalmFit.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import palmfit.PalmFit.entities.User;
import palmfit.PalmFit.exceptions.NotFoundException;
import palmfit.PalmFit.payloads.exceptions.ProfileDTO;
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
    private MembershipService membershipService;
    public UserService(@Lazy MembershipService membershipService){
        this.membershipService = membershipService;
    }
    public User findById(UUID id) {
        return userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public User findByEmail(String email){
        return userDAO.findByEmail(email).orElseThrow( () -> new NotFoundException("Email " + email + " not found!"));
    }
    public Page<User> getUsers(int page, int size, String orderBy){
        if(size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }

    public List<User> getUsersById(List<UUID> usersIds){
        return userDAO.findAllById(usersIds);
    }

    public void findByIdAndDelete(UUID id){
        User found = this.findById(id);
        userDAO.delete(found);
    }

    public ProfileDTO getProfile(User user){
        ProfileDTO profileDTO = new ProfileDTO(user.getName(), user.getLastname(), user.getAge(), user.getEmail(), user.getAvatar());
        return profileDTO;
    }

    public User findByIdAndUpdate(UUID id, UserDTO body){
        User found = this.findById(id);
        found.setName(body.name());
        found.setLastname(body.lastname());
        found.setAge(body.age());
        found.setEmail(body.email());
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
