package com.liyuan.config;

import com.liyuan.component.jwt.JwtTokenGenerator;
import com.liyuan.filter.JwtAuthenticationFilter;
import com.liyuan.handler.CustomLogoutHandler;
import com.liyuan.handler.CustomLogoutSuccessHandler;
import com.liyuan.handler.SimpleAccessDeniedHandler;
import com.liyuan.handler.SimpleAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_PROCESSING_URL = "/process";

//    @Bean
//    public JsonLoginPostProcessor jsonLoginPostProcessor() {
//        return new JsonLoginPostProcessor();
//    }

//    @Autowired
//    Collection<LoginPostProcessor> loginPostProcessors;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Resource
    JwtTokenGenerator jwtTokenGenerator;

    @Resource
    AuthenticationFailureHandler jsonAuthenticationFailureHandler;

    @Resource
    AuthenticationSuccessHandler jsonAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new SimpleAuthenticationEntryPoint())
                .accessDeniedHandler(new SimpleAccessDeniedHandler())
                .and()
                .authorizeRequests().antMatchers("/login.html").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
//                .addFilterBefore(new PreLoginFilter(LOGIN_PROCESSING_URL, loginPostProcessors), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
//                .successForwardUrl("/login/success")
//                .failureForwardUrl("/login/failure")
                .successHandler(jsonAuthenticationSuccessHandler)
                .failureHandler(jsonAuthenticationFailureHandler)
                .and().logout().logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .addLogoutHandler(new CustomLogoutHandler());
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenGenerator), UsernamePasswordAuthenticationFilter.class);

    }

}
