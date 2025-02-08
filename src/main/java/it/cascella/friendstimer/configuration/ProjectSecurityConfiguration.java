package it.cascella.friendstimer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfiguration {


    @Profile("https")
    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {

        http.requiresChannel((requiresChannel) -> requiresChannel.anyRequest().requiresSecure());
        http.csrf(cc -> cc.disable());
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/account","balance","user/hello").authenticated()//necessitano del form login NON NECESSITANO DEL /
                .requestMatchers("/cards","/contact","/error","/register").permitAll()//non necessitano di autenticazione
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
    public PasswordEncoder passwordEncoder(){
        String idForEncode = "bcrypt";
        Map<String,PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        return new DelegatingPasswordEncoder(idForEncode,encoders);
    }
}
