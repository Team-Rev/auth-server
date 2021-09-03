package rev.team.AUTHSERVER.contorller;

import org.springframework.web.bind.annotation.*;
import rev.team.AUTHSERVER.domain.RevUser;
import rev.team.AUTHSERVER.domain.request.FindIdReq;
import rev.team.AUTHSERVER.domain.request.FindPwReq;
import rev.team.AUTHSERVER.domain.request.UpdatePwReq;
import rev.team.AUTHSERVER.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public RevUser getUser(@RequestParam("username") String username){
        return userService.findUser(username).orElseThrow(RuntimeException::new);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody RevUser user){
        return userService.save(user);
    }

    @GetMapping("/nickname")
    public String getNickname(@RequestParam String username){
        return userService.findUser(username).orElseThrow(RuntimeException::new).getNickname();
    }

    @GetMapping("/userPoint")
    public Long getPoint(@RequestParam String username) {
        return userService.findUser(username).orElseThrow(RuntimeException::new).getPoint();
    }

    @PostMapping("/findId")
    public String findId(@RequestBody FindIdReq findIdReq) {
        return userService.findId(findIdReq);
    }

    @PostMapping("/findPw")
    public String findPw(@RequestBody FindPwReq findPwReq) {
        return userService.findPw(findPwReq);
    }

    // TODO : 사용자 유무 확인 후 비밀번호 변경
    @PatchMapping("/updatePw")
    public String changeNewPw(@RequestBody UpdatePwReq updatePwReq) {
        return userService.changeNewPw(updatePwReq); }
}