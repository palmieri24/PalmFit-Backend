package palmfit.PalmFit.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import palmfit.PalmFit.entities.Membership;
import palmfit.PalmFit.enums.MembershipType;

import java.util.List;
import java.util.UUID;

@Repository
public interface MembershipDAO extends JpaRepository<Membership, UUID> {
    @Query("SELECT m FROM Membership m WHERE m.user.id = :user_id AND m.membershipType = :membershipType")
    List<Membership> findMembershipByIdAndType(@Param("user_id") UUID id, @Param("membershipType") MembershipType  membershipType);
    @Query("SELECT m FROM Membership m WHERE m.user.id = :user_id AND m.exp_date >= CURRENT_DATE")
    List<Membership> findActiveMembershipByUserId(@Param("user_id") UUID user_id);
}
