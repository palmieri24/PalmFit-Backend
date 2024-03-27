package palmfit.PalmFit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import palmfit.PalmFit.entities.Membership;
import palmfit.PalmFit.entities.User;
import palmfit.PalmFit.exceptions.BadRequestException;
import palmfit.PalmFit.exceptions.UnauthorizedException;
import palmfit.PalmFit.payloads.exceptions.MembershipDTO;
import palmfit.PalmFit.payloads.exceptions.MembershipResponseDTO;
import palmfit.PalmFit.services.MembershipService;

import java.util.UUID;

@RestController
@RequestMapping("/memberships")
public class MembershipController {
  @Autowired
  private MembershipService membershipService;

  @GetMapping
  public Page<Membership> getMemberships(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String orderBy){
    return membershipService.getMemberships(page, size, orderBy);
  }

  @GetMapping("/{id}")
  public Membership getMembership(@PathVariable UUID id){
    return membershipService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Membership save(@AuthenticationPrincipal User currentUser, @RequestBody MembershipDTO body){
    if (currentUser == null){
      throw new UnauthorizedException("User not Authenticated!");
    }
      return membershipService.save(body, currentUser);
}
  @PutMapping("/renewMe")
  public Membership findByIdAndUpdate(@AuthenticationPrincipal User currentUser, @RequestBody MembershipDTO body){
    if (currentUser == null){
      throw new UnauthorizedException("User not Authenticated!");
    }
    return membershipService.findByIdAndUpdate(body, currentUser.getId());
  }
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority('ADMIN')")
  public void findByIdAndDelete(@PathVariable UUID id){
    membershipService.findByIdAndDelete(id);
  }
}



