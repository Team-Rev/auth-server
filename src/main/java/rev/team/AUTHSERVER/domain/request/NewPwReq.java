package rev.team.AUTHSERVER.domain.request;

import lombok.Getter;

@Getter
public class NewPwReq {
    private String userId;
    private String newPassword;
}
