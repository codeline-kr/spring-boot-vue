package kr.codeline.demo.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import kr.codeline.demo.common.enums.UserType;

/**
 * 컨트롤러에서 로그인 사용자 정보 가져올때 쓰는 용도 public void main(@LoginUser Data userinfo)
 * public void main(@LoginUser(target=UserType.ADMIN) Data userinfo)
 * 
 * @author 김영명
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
    String value() default "";

    UserType target() default UserType.ADMIN;
}