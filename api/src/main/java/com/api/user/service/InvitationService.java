package com.api.user.service;

import com.api.common.dto.SuccessResponse;
import com.api.common.exception.ForbiddenException;
import com.api.common.exception.ValidationException;
import com.api.user.entity.Invitation;
import com.api.user.entity.User;
import com.api.user.repository.InvitationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InvitationService {

  // Don't user service here
  private final InvitationRepository invitationRepository;

  @PersistenceContext
  private EntityManager entityManager;


  public SuccessResponse inviteUser(String email, Long userId) {

    if (invitationRepository.existsByEmail(email)) {
      throw new ValidationException("error.email.exist");
    }

    User user = entityManager.getReference(User.class, userId);

    if (user.getEmail().equals(email)) {
      throw new ForbiddenException(); // can't invite yourself
    }

    Invitation invitation = Invitation.builder().email(email).inviter(user).build();

    invitationRepository.save(invitation);

    // TODO: send a mail

    return new SuccessResponse("success");
  }

  public void ensureNotExistingInvitationCode(String email) {
    if (invitationRepository.existsByRefCode(email)) {
      throw new ValidationException("error.invitation.exist");
    }
  }

  public Invitation getInvitationByCode(String refCode) {
    return invitationRepository.findByRefCode(refCode).orElseThrow(() -> new ValidationException("error.invitation.not_found"));
  }

  public void ensureActive(Invitation invitation) {
    if (invitation.getActive() != Boolean.TRUE) throw new ValidationException("error.invitation.invalid");
  }

  public void deactivateInvitation(String refCode) {
    Invitation invitation = getInvitationByCode(refCode);
    ensureActive(invitation);

    invitation.setActive(false);
    invitationRepository.save(invitation);
  }

}
