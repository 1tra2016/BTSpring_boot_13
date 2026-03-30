package springboot.ss11.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import springboot.ss11.entity.User;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.user = user;

        // Convert role -> GrantedAuthority
        this.authorities = user.getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .toList();
    }

    // 🔑 Lấy quyền
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // 🔑 Password để Spring so sánh
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 🔑 Username để login
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 🔒 Account có bị khóa không
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 🔑 Quan trọng: enable/disable user
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}