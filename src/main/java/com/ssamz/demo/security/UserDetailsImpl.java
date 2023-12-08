package com.ssamz.demo.security;

import com.ssamz.demo.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private User user;

    public UserDetailsImpl(User user)
    {
        this.user = user;
    }

    @Override
    public String getPassword()
    {
        return user.getPassword();
        /*// noop: 암호화하지 않기 위한 설정 / 로그인 인증 시 사용자가 입력한 비밀번호를 암호화 하여 비교하기 위해 삭제
        return "{noop}" + user.getPassword();*/
    }

    @Override
    public String getUsername()
    {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지 반환
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    // 계정이 잠겨있는지 반환
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 반환
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    // 계정이 활성화되었는지 반환
    public boolean isEnabled()
    {
        return true;
    }

    // 계정이 가지고 있는 권한 목록 저장하여 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        // 권한 목록
        Collection<GrantedAuthority> roleList = new ArrayList<>();

        // 권한 목록 설정
        roleList.add(new GrantedAuthority()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public String getAuthority()
            {
                return "ROLE_" + user.getRole();
            }
        });

        return roleList;
    }
}
