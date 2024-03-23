package palmfit.PalmFit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import palmfit.PalmFit.entities.Message;

import java.util.UUID;

public interface MessageDAO extends JpaRepository<Message, UUID> {
}
