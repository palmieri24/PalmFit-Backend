package palmfit.PalmFit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "messages")
@Entity
public class Message {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String email;
    private String message;
}
