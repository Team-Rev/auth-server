package rev.team.AUTHSERVER.domain.request;

import lombok.Getter;

@Getter
public class FindPwReq {
    private String userId;
    private String name;
    private String phone;
}
