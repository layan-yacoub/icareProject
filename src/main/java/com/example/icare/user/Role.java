package com.example.icare.user;


public enum Role {
    PATIENT,
    NUTRITIONIST,
    RESTAURANT,
    USER,
    ADMIN(
           /* Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )*/
    ),
    MANAGER(
            /*
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )*/
    )


/*
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }*/


}
// PATIENT,
//   NUTRITIONIST,
//    RESTAURANT,
//    ADMIN;
