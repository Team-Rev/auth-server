package rev.team.AUTHSERVER.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
@Entity
@Table(name = "rev_user")
public class RevUser implements UserDetails {

    @Id
    private String userId; // EMAIL

    private String password;

    private String nickname;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate DOB;

    private String phone;

    private String address;

    private String detailAddress;

    private String postNumber;

    private Long point;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name="user_id"))
    private Set<RevAuthority> authorities;

    @Column(columnDefinition = "boolean default true")
    private boolean enabled;

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    public boolean isRightFormat(){
        System.out.println(this.toString());
        if(!isValidEmail(this.userId)) return false; // 이메일 형식 맞는지
        if(!checkLength(this.password, 8)) return false; // 패스워드 길이 8자 이상인지
        if(!checkLength(this.nickname, 2)) return false; // 닉네임 길이 2자 이상인지
        if(!checkLength(this.name, 2)) return false; // 이름 길이 2자 이상인지
        if(!isValidPhone(this.phone)) return false; // 휴대폰 형식 맞는지 이상인지
        if(DOB == null) return false; //생일이 비어 있는지
        if(address == null) return false; // 주소가 비어있는지
        if(detailAddress == null) return false; // 주소가 비어있는지
        if(postNumber == null) return false; // 주소가 비어있는지

        return true;
    }

    public boolean isValidEmail(String email){
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean isValidPhone(String phone){
        String regex = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public boolean checkLength(String str, int length){
        return str.length() >= length;
    }
}
