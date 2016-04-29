package com.rbc.controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @RequestMapping("/user")
    public Map<String, String> user(Principal user) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("username", user.getName());
        if (user instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken enhancedUser = (UsernamePasswordAuthenticationToken) user;
            List<String> roleList = enhancedUser.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList());
            resultMap.put("roles", roleList.toString());
        }
        return resultMap;
    }
}
