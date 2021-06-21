package rev.team.AUTHSERVER.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="rev_user_authority")
@IdClass(RevAuthority.class)
public class RevAuthority implements GrantedAuthority {

    @Id
    @Column(name="user_id")
    private String userId;

    @Id
    private String authority;

}
