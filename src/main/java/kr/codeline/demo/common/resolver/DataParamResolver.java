package kr.codeline.demo.common.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import kr.codeline.demo.common.util.Data;

@Component
public class DataParamResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == Data.class;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer mavc, NativeWebRequest req,
            WebDataBinderFactory wbf) throws Exception {

        Data result = new Data();

        req.getParameterNames().forEachRemaining(name -> {
            result.put(name, req.getParameter(name));
        });

        return result;
    }
}