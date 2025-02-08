package it.cascella.friendstimer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity


public class ProjectSecurityConfiguration {



    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(cc -> cc.disable());
        http.cors((cors) -> cors.configurationSource(corsConfigurationSource()));
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/account","balance","/user/**").authenticated()//necessitano del form login NON NECESSITANO DEL /
                .requestMatchers(HttpMethod.OPTIONS,"/cards","/contact","/error","/register","/options/**").permitAll()

        );
        http.formLogin(withDefaults());
        //http.formLogin(flc -> flc.disable()); //this will disable the form login

        http.sessionManagement(sessionManagement -> sessionManagement
                .sessionFixation().newSession()
                .invalidSessionUrl("/timeout")
                .maximumSessions(1) //numero massimo di sessioni per utente
                .maxSessionsPreventsLogin(true) // l'effetto sarà che se due utenti sono già loggati al terzo non verrà permesso l'accesso
                .expiredUrl("/expired") //pagina a cui verrà reindirizzato l'utente se la sessione è scaduta
        );


        http.httpBasic(withDefaults());
        //http.httpBasic(hbc -> hbc.disable()); //this will disable the http basic authentication
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:550"));
         // Aggiungi il tuo frontend
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "X-XSRF-TOKEN",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Methods",
                "Access-Control-Allow-Credentials",
                "Access-Control-Expose-Headers",
                "Access-Control-Max-Age",
                "Access-Control-Request-Headers",
                "Access-Control-Request-Method",
                "Origin"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "X-XSRF-TOKEN",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Methods",
                "Access-Control-Allow-Credentials",
                "Access-Control-Expose-Headers",
                "Access-Control-Max-Age",
                "Access-Control-Request-Headers",
                "Access-Control-Request-Method",
                "Origin"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        String idForEncode = "bcrypt";
        Map<String,PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        return new DelegatingPasswordEncoder(idForEncode,encoders);
    }
}
