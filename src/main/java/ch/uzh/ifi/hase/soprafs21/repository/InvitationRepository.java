package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("invitationRepository")
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    //Implemented:
    Optional<Invitation> findByInvitationId(Long id);

    // Delete Function
    void deleteByGameId(Long gameId);
}

