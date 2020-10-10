package kr.codeline.demo.web.common;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codeline.demo.common.annotation.ClientIp;
import kr.codeline.demo.module.user.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService service;

    @PostMapping("/m/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body, @ClientIp String ip) {
        String token = service.adminLogin(body.get("id"), body.get("password"), ip);

        if (token == null) {
            return new ResponseEntity<>(Map.of("message", "아이디/비밀번호를 확인해 주세요."), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}
