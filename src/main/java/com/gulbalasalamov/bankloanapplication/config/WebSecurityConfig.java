package com.gulbalasalamov.bankloanapplication.config;

import com.gulbalasalamov.bankloanapplication.security.JwtTokenFilterConfigurer;
import com.gulbalasalamov.bankloanapplication.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); // prevent others accessing your tokens and make requests by using links
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session will be created or used by Spring security

        //Entry points
        http.authorizeRequests()
                .antMatchers("/users/signin").permitAll()
                .antMatchers("/users/signup").permitAll()
                .anyRequest()
                .authenticated(); // authenticate means no role is important. login enough

//        http
//                .httpBasic()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/xxx").permitAll() // no login necassary. let everyone access this endpoint. for exp login page
//                .antMatchers(HttpMethod.GET, "/v1/buyer/**").hasRole("USER")// to which url who will access and do what. **-> all metods
//                .antMatchers(HttpMethod.POST, "/v1/item/**").hasRole("ADMIN")
//                .and()
//                .csrf().disable();

        //Apply jwt
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
//                swagger2 base urls
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
//                swagger3 base urls
                "/swagger-ui/**",
                "/javainuse-openapi/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
