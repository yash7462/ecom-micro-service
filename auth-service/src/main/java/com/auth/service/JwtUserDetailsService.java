package com.auth.service;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.repository.UserRepository;
import com.auth.modal.CustomUser;
import com.auth.modal.Permission;
import com.auth.modal.UserModal;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public CustomUser loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserModal user = userRepository.findByUserName(userName);
        if (user == null)
			throw new UsernameNotFoundException("Username not Found");
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
//        List<UUID> rolesId=  userRoleRepository.findByUserId(user.getId()).stream()
//                .map(UserRole::getRoleId).collect(Collectors.toList());
		List<Permission> permissions = new ArrayList<Permission>();
		Permission per = new Permission();
		per.setName("ROLE_USER");
		permissions.add(per);
        return CustomUser.build(user,  permissions);
    }

//    public    List<Permission>getUserRole(List<UUID> roleIds) {
//        List<RolePermission> rolePermission = rolePermissionRepository.findByRoleIdIn(roleIds);
//        List<UUID> permissionId = rolePermission.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
//        List<Permission> permissions = permissionRepository.findAllByIdIn(permissionId);
//        //List<String> authority = permissions.parallelStream().map(Permission::getName).collect(Collectors.toList());
//        return permissions;
//    }

}
