package kr.codeline.demo.common.resolver;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import kr.codeline.demo.common.annotation.LoginUser;
import kr.codeline.demo.common.enums.UserType;
import kr.codeline.demo.common.util.Data;

/**
 * 세션에 있는 로그인 유저 정보 가져오는 용도로 씀
 * 
 * public main(@LoginUser Data<String, Object> user)
 * 
 * public main(@LoginUser("id") String id)
 * 
 * 
 * public main(@LoginUser(value = "id", target = UserType.User) String id)
 * 
 */
@Component
public class LoginUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer mavc, NativeWebRequest req,
            WebDataBinderFactory wbf) throws Exception {

        LoginUser annotation = param.getParameterAnnotation(LoginUser.class);
        Object resolved = null;
        Map user = null;

        /**
         * target 에 해당되는 세션 가져오기
         */
        switch (annotation.target()) {
            case USER:
                user = (Map) req.getAttribute(UserType.USER.getName(), WebRequest.SCOPE_SESSION);
                break;
            case ADMIN:
                user = (Map) req.getAttribute(UserType.ADMIN.getName(), WebRequest.SCOPE_SESSION);
                break;
            default:
                break;
        }

        // 파라메터 타입에 따라 전체 리턴할건지 특정 필드값만 리턴할건지
        if (user != null) {
            if (Data.class.isAssignableFrom(param.getParameterType())) {
                resolved = new Data<>(user);
            } else if (String.class.isAssignableFrom(param.getParameterType())) {
                resolved = annotation.value().isEmpty() ? null : user.get(annotation.value());
            }
        }

        return resolved;
    }
}