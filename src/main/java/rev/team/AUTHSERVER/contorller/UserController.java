package rev.team.AUTHSERVER.contorller;

import org.springframework.web.bind.annotation.*;
import rev.team.AUTHSERVER.domain.RevUser;
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

}
