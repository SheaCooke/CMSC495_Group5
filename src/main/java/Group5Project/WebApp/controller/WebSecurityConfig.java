package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.UserModelCollection;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Properties;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@NoArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf()
                .ignoringAntMatchers("/register", "/admin/AddNewItem", "/admin/UpdateItem", "/admin/DeleteItem/*")
                .and()
                .authorizeRequests((requests) -> requests
                        .antMatchers("/admin").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST,"/admin/AddNewItem").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST,"/admin/UpdateItem").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST,"/admin/DeleteItem/*").hasRole("ADMIN")
                        .antMatchers("/query").hasRole("ADMIN")
                        .antMatchers("/Cart").hasRole("USER")
                        .antMatchers("/PastAndPendingOrders").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/register").anonymous().anyRequest().authenticated()

                )
                .formLogin((form) -> form
                        .loginPage("/Login")
                                .loginProcessingUrl("/process-login")
                                .defaultSuccessUrl("/")
                                .failureUrl("/Login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/Logout")
                        .logoutSuccessUrl("/Login")
                        .invalidateHttpSession(true));

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        UserDetails user =
                User.withUsername("user")
                        .password(encoder.encode("password"))
                        .roles("USER")
                        .build();

        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

}