package palmfit.PalmFit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import palmfit.PalmFit.entities.Membership;
import palmfit.PalmFit.entities.User;
import palmfit.PalmFit.enums.MembershipType;
import palmfit.PalmFit.exceptions.NotFoundException;
import palmfit.PalmFit.exceptions.UnauthorizedException;
import palmfit.PalmFit.payloads.exceptions.MembershipDTO;
import palmfit.PalmFit.repositories.MembershipDAO;
import palmfit.PalmFit.repositories.UserDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class MembershipService {
    @Autowired
    private MembershipDAO membershipDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDAO userDAO;

    public Page<Membership> getMemberships(int page, int size, String orderBy){
        if (size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return membershipDAO.findAll(pageable);
    }

    public Membership findById(UUID id){
        return membershipDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID id){
        Membership found = this.findById(id);
        membershipDAO.delete(found);
    }

    public Membership save(MembershipDTO body, User user){
        System.out.println(body);
        if (user == null){
            throw new UnauthorizedException("User not authenticated!");
        }
        Membership membership = new Membership();
        membership.setMembershipType(body.membershipType());
        membership.setPrice(mbsPrice(body.membershipType()));
        membership.setStart_date(LocalDate.now());
        membership.setExp_date(calculateExp(membership.getStart_date(), membership.getMembershipType()));
        User found = userService.findById(user.getId());
        found.setMembership(membership);
        membership.getUsers().add(found);
        membershipDAO.save(membership);
        userDAO.save(found);
        return membership;
    }

    private LocalDate calculateExp(LocalDate start_date, MembershipType membershipType){
        return switch (membershipType){
            case MONTHLY -> start_date.plusMonths(1);
            case THREE_MONTH -> start_date.plusMonths(3);
            case ANNUAL -> start_date.plusYears(1);
        };
    }

    private int mbsPrice(MembershipType membershipType){
        return switch (membershipType){
            case MONTHLY -> 50;
            case THREE_MONTH -> 120;
            case ANNUAL -> 360;
        };
    }
    public Membership findByIdAndUpdate( MembershipDTO body, UUID id){
        Membership membership = membershipDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        membership.setMembershipType(body.membershipType());
        membership.setPrice(mbsPrice(body.membershipType()));
        membership.setStart_date(LocalDate.now());
        membership.setExp_date(calculateExp(membership.getStart_date(), membership.getMembershipType()));
        return membershipDAO.save(membership);
    }

}
