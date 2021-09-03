package rev.team.AUTHSERVER.domain.request;

import lombok.Getter;

@Getter
public class UpdatePwReq {
    private String userId;
    private String newPassword;
}
