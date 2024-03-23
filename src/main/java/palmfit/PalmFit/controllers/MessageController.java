package palmfit.PalmFit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import palmfit.PalmFit.entities.Message;
import palmfit.PalmFit.repositories.MessageDAO;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageDAO messageDAO;

    @PostMapping
    public Message sendMessage(@RequestBody Message message){
        return messageDAO.save(message);
    }
}
