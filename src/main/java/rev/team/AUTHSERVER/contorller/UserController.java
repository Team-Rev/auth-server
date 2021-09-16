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

    @GetMapping("/userInfo")
    public RevUser getUser(@RequestParam String userId){
        return userService.findUser(userId).orElseThrow(RuntimeException::new);
    }

    @PatchMapping("userInfo")
    public String updateUser(@RequestBody RevUser user) {
        return userService.updateUser(user);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody RevUser user){
        return userService.save(user);
    }

    @GetMapping("/nickname")
    public String getNickname(@RequestParam String userId){
        return userService.findUser(userId).orElseThrow(RuntimeException::new).getNickname();
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

    @PatchMapping("/updatePw")
    public String updatePw(@RequestBody UpdatePwReq updatePwReq) {
        return userService.updatePw(updatePwReq); }
}