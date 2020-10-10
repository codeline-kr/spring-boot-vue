package kr.codeline.demo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.codeline.demo.common.resolver.ClientIpResolver;
import kr.codeline.demo.common.resolver.DataParamResolver;
import kr.codeline.demo.common.resolver.LoginUserResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginUserResolver loginUserResolver;

    @Autowired
    private ClientIpResolver clientIpResolver;

    @Autowired
    private DataParamResolver dataParamResolver;

    /**
     * interceptor 등록
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    /**
     * controller 에서 쓰는 argument resolver 등록
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserResolver);
        resolvers.add(clientIpResolver);
        resolvers.add(dataParamResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }
}