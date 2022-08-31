package vn.compedia.api.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.Account;
import vn.compedia.api.exception.user.UserNotFoundException;
import vn.compedia.api.service.ApiUserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BundleUserDetailsService implements UserDetailsService {

    private ApiUserService apiUserService;

    @Autowired
    public BundleUserDetailsService(ApiUserService apiUserService) {
        this.apiUserService = apiUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String credential) throws UsernameNotFoundException {
        try {
            credential = credential.trim();
            Account account = null;
            if (credential.indexOf('@') > 0) {
                account = apiUserService.findByEmail(credential);
            }
            if (account == null) {
                throw new UsernameNotFoundException("Username: " + credential + " not found");
            }

            return new BundleUserDetails(account, getAuthorities(account.getRoleId()));
        } catch (UserNotFoundException exception) {
            throw new UsernameNotFoundException("Username: " + credential + " not found");
        }
    }

    private List<GrantedAuthority> getAuthorities(Integer roleId) {
        return getGrantedAuthorities(roleId);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Integer roleId) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(roleId)));
        return authorities;
    }

    public static class BundleUserDetails implements UserDetails {

        private static final long serialVersionUID = -3542337090559589236L;

        private Account account;
        private List<GrantedAuthority> authorities;

        BundleUserDetails(Account account, List<GrantedAuthority> authorities) {
            this.account = account;
            this.authorities = authorities;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return account.getPassword();
        }

        @Override
        public String getUsername() {
            return account.getEmail();
        }

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

        @Override
        public boolean isEnabled() {
            return true;
        }

        public Account getAccount() {
            return account;
        }
    }
}

