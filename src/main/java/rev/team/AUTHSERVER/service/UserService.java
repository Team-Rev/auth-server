package rev.team.AUTHSERVER.service;

import org.springframework.stereotype.Service;
import rev.team.AUTHSERVER.domain.RevAuthority;
import rev.team.AUTHSERVER.domain.RevUser;
import rev.team.AUTHSERVER.domain.request.FindIdReq;
import rev.team.AUTHSERVER.domain.request.FindPwReq;
import rev.team.AUTHSERVER.domain.request.UpdatePwReq;
import rev.team.AUTHSERVER.repository.RevUserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final RevUserRepository userRepository;
    public UserService(RevUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<RevUser> findUser(String id) {
        return userRepository.findRevUserByUserId(id);
    }

    public String save(RevUser user) {
        if(!user.isRightFormat()) return "FAIL TO isRightFormat";
        if(userRepository.existsById(user.getUserId())) return "ID is present";
        user.setEnabled(true);
        user.setPoint(0L);
        RevUser newUser = userRepository.save(user);
        addAuthority(newUser.getUserId(), "USER");
        return "SUCCESS";
    }

    public void addAuthority(String userId, String authority){

        userRepository.findById(userId).ifPresent(user->{
            RevAuthority newRole = new RevAuthority(user.getUserId(), authority);
            if(user.getAuthorities() == null){
                HashSet<RevAuthority> authorities = new HashSet<>();
                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            }else if(!user.getAuthorities().contains(newRole)){
                HashSet<RevAuthority> authorities = new HashSet<>(user.getAuthorities());
                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            }
        });
    }

    public void removeAuthority(String userId, String authority){
        userRepository.findById(userId).ifPresent(user->{
            if(user.getAuthorities()==null) return;
            RevAuthority targetRole = new RevAuthority(user.getUserId(), authority);
            if(user.getAuthorities().contains(targetRole)){
                user.setAuthorities(
                        user.getAuthorities().stream().filter(auth->!auth.equals(targetRole))
                                .collect(Collectors.toSet())
                );
                save(user);
            }
        });
    }

    public String findId(FindIdReq findIdReq) {
        Optional<RevUser> revUser = userRepository.findUserIdByNameAndPhone(findIdReq.getName(), findIdReq.getPhone());

        if (revUser.isEmpty()) {
            return "USER NOT FOUND";
        } else {
            return revUser.get().getUsername();
        }
    }

    public String findPw(FindPwReq findPwReq) {
        Optional<RevUser> revUser = userRepository.findRevUserByNameAndUserIdAndPhone(findPwReq.getName(), findPwReq.getUserId(), findPwReq.getPhone());

        if (revUser.isEmpty()) {
            return "USER NOT FOUND";
        } else {
            return "OK";
        }
    }

    public String changeNewPw(UpdatePwReq updatePwReq) {
        RevUser user = userRepository.findById(updatePwReq.getUserId()).get();

        if (user == null) {
            return "USER NOT FOUND";
        } else {
            user.setPassword(updatePwReq.getNewPassword());
            userRepository.save(user);

            return "UPDATE SUCCESS";
        }
    }
}