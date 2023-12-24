package com.kan.identity.controller;

import com.kan.identity.model.Identity;
import com.kan.identity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class UserController {
    @Autowired
    UserService service;
    @GetMapping("authenticated")
    public ResponseEntity<Identity> authenticated() {
        return ResponseEntity.ok(service.authenticated());
    }

    @GetMapping("vendors")
    public ResponseEntity<Identity> getVendors() {
        return ResponseEntity.ok(service.getVendors());
    }

    @GetMapping("vendors/{vid}")
    public ResponseEntity<Identity>  getVendorsDetail(@PathVariable String vid) {
        return ResponseEntity.ok(service.getVendorsDetail(vid));
    }
    @GetMapping("unauthenticated")
    public ResponseEntity<Identity> unauthenticated() {
        return ResponseEntity.ok(service.unauthenticated());
    }
}