package Group5Project.WebApp.controller;

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
                .ignoringAntMatchers("/register", "/admin/AddNewItem")
                .and()
                .authorizeRequests((requests) -> requests
                        .antMatchers("/admin").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST,"/admin/AddNewItem").hasRole("ADMIN")
                        .antMatchers("/query").hasRole("ADMIN")
                        .antMatchers("/Cart").hasRole("USER")
                        .antMatchers("/PastAndPendingOrders").hasRole("USER")
                        .antMatchers(HttpMethod.POST, "/register").anonymous().anyRequest().authenticated()

                )
                .formLogin((form) -> form
                        .loginPage("/Login")
                                .loginProcessingUrl("/process-login")
                                .defaultSuccessUrl("/")
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


        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();
//encoder().encode("adminPass")
        return new InMemoryUserDetailsManager(user, admin);
    }

//
//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//
//        //@formatter:off
//        http.authorizeRequests()
//                .antMatchers("/Login").permitAll()
//                .antMatchers("/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers("/admin/**").hasAnyRole("ADMIN")
//                .and()
//                .formLogin()
//                .loginPage("/Login")
//                .loginProcessingUrl("/process-login")
//                .defaultSuccessUrl("/")
//                .failureUrl("/login?error=true")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login?logout=true")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .permitAll()
//                .and()
//                .csrf()
//                .disable();
//        //@formatter:on
//    }
//
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring()
//                .antMatchers("/resources/**", "/static/**");
//    }

}