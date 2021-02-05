package vn.compedia.api.util.user;

import org.springframework.security.core.context.SecurityContextHolder;
import vn.compedia.api.entity.Account;
import vn.compedia.api.service.authentication.BundleUserDetailsService;

public class UserContextHolder {

    private UserContextHolder() {
    }

    public static Account getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BundleUserDetailsService.BundleUserDetails userDetails = (BundleUserDetailsService.BundleUserDetails) principal;
        return userDetails.getAccount();
    }
}
