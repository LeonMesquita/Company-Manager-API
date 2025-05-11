package com.company_manager.api.security;

import com.company_manager.api.models.enums.ProfileEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class UserSpringSecurity implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSpringSecurity(Long id, String username, String password, Set<ProfileEnum> profileEnums) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.authorities = profileEnums.stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toSet());
    }

    public boolean hasRole(ProfileEnum profileEnum) {
        return getAuthorities().contains(new SimpleGrantedAuthority(profileEnum.getDescription()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
        return true;
    }


    @Override
    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
        return true;
    }
}
