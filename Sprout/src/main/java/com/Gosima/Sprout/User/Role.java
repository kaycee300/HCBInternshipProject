
package com.Gosima.Sprout.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.List;

public enum Role {

    ADMIN(List.of(
            new SimpleGrantedAuthority(Permission.READ_PRODUCT),
            new SimpleGrantedAuthority(Permission.MANAGE_PRODUCT),
            new SimpleGrantedAuthority(Permission.MANAGE_USER),
            new SimpleGrantedAuthority(Permission.VIEW_ORDER)

    )),

    CUSTOMER(List.of(
            new SimpleGrantedAuthority(Permission.READ_PRODUCT),
            new SimpleGrantedAuthority(Permission.PLACE_ORDER)

    )),

    VENDOR(List.of(

            new SimpleGrantedAuthority(Permission.READ_PRODUCT),
            new SimpleGrantedAuthority(Permission.MANAGE_PRODUCT),
            new SimpleGrantedAuthority(Permission.VIEW_ORDER)




    ));

    private List<GrantedAuthority> permissions;

    Role(List<GrantedAuthority> permissions) {
        this.permissions = permissions;
    }


    public List<GrantedAuthority> getAuthorities() {
        return permissions;
    }
}
