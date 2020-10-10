package kr.codeline.demo.module.user.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codeline.demo.common.service.JWTService;
import kr.codeline.demo.common.util.Util;
import kr.codeline.demo.module.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 김영명
 */
@Service
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private JWTService jwt;

    /**
     * 관리자 로그인
     */
    public String adminLogin(String id, String password, String ip) {

        // Map<String, String> admin = mapper.adminLogin(id);
        Map<String, String> admin = Map.of("id", "admin", "name", "관리자", "password",
                "$2a$10$WlsZg4YDHBysKecou5M/Led1FZEdjeLLufYPisQJrsSrCk0xw04Vq");

        log.debug("login user : {}", admin);

        if (admin != null && Util.verifyEncrypt(password, admin.get("password"))) {
            Map<String, String> user_info = Map.of("id", admin.get("id"), "name", admin.get("name"));

            return jwt.token(user_info);
        } else {
            return null;
        }
    }
}