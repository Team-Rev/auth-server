package rev.team.AUTHSERVER.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rev.team.AUTHSERVER.domain.RevUser;

import java.util.Optional;

public interface RevUserRepository extends JpaRepository<RevUser, String> {
    Optional<RevUser> findRevUserByUserId(String id);
}
