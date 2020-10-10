package kr.codeline.demo.common.enums;

/**
 * 시스템 사용자 타입
 * 
 * 로그인시 세션에 저장할 에트리뷰트 이름, Interceptor, @LoginUser 의 target 으로 사용됨
 * 
 * @author 김영명
 */
public enum UserType {
    ADMIN("ADMIN"), USER("USER");

    final private String name;

    public String getName() {
        return name;
    }

    private UserType(String name) {
        this.name = name;
    }
}