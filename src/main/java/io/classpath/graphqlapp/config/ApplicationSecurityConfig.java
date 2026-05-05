package io.classpath.graphqlapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableMethodSecurity
public class ApplicationSecurityConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/graphql")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    //completes our authentication
    public UserDetailsService userDetailsService(){
        UserDetails admin = User.builder().username("admin").password("{noop}admin123").roles("ADMIN", "USER").build();
        UserDetails superAdmin = User.builder().username("super-admin").password("{noop}admin123").roles("ADMIN", "SUPER_ADMIN", "USER").build();
        UserDetails user = User.builder().username("user").password("{noop}user123").roles("USER").build();

        return new InMemoryUserDetailsManager(user, admin, superAdmin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/graphql","/graphql**", "/graphql/**", "/grapihql","/graphiql**","/graphiql/**", "/graphiql/**", "/about-us/**", "/login**", "/logount**", "/contact-us/**").permitAll()
                        .anyRequest()
                        .authenticated()
                ).httpBasic(Customizer.withDefaults());

        return httpSecurity.build();

    }

}
