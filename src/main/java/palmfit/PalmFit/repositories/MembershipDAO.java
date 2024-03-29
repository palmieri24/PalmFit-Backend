package palmfit.PalmFit.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import palmfit.PalmFit.entities.Membership;
import palmfit.PalmFit.entities.User;
import palmfit.PalmFit.enums.MembershipType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MembershipDAO extends JpaRepository<Membership, UUID> {
    Optional<Membership> findByUsers(User user);
}
