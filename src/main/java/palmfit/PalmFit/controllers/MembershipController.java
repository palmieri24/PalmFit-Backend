package palmfit.PalmFit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import palmfit.PalmFit.entities.Membership;
import palmfit.PalmFit.exceptions.BadRequestException;
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
  public Membership save(@RequestBody @Validated MembershipDTO body, BindingResult validation){
    if(validation.hasErrors()){
      System.out.println(validation.getAllErrors());
      throw new BadRequestException("Payload Error!");
  } else {
      return membershipService.save(body);
    }
}
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public MembershipResponseDTO findByIdAndUpdate(@PathVariable UUID id,@RequestBody @Validated MembershipDTO body, BindingResult validation){
    if(validation.hasErrors()){
      throw new BadRequestException("Errori di validazione");
    }
    Membership membership = membershipService.findByIdAndUpdate(id, body);
    return new MembershipResponseDTO(membership.getId());
  }
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority('ADMIN')")
  public void findByIdAndDelete(@PathVariable UUID id){
    membershipService.findByIdAndDelete(id);
  }
}



