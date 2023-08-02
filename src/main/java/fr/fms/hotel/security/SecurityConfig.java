package fr.fms.hotel.security;

import fr.fms.hotel.security.entities.AppUser;
import fr.fms.hotel.security.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity        //désactive le formulaire d'authentification par défaut de spring et active notre stratégie de sécurité
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AccountService accountService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {  //autre moyen pour demander à spring de récupérer les credentials d'un user
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                try {
                    //c'est à nous de lui indiquer comment chercher un user
                    AppUser user = accountService.findUserByUsername(username);
                    Collection<GrantedAuthority> authorities = new ArrayList<>();      //conversion des roles d'un user en grantedAuthorities dont spring a besoin
                    user.getRoles().forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role.getRolename()));
                    });
                    return new User(user.getUsername(),user.getPassword(),authorities);
                }
                catch(Exception e) {
                    log.error("PB Utilisateur inconnu -> {}",e.getMessage());
                }
                return null;
            }
        });
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        //l'utilisateur qui souhaite s'authentifier va saisir un mot de passe qui sera crypté pour être comparé au mot de passe stocké en base
    }*/

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();    //Désactiver la génération automatique du synchronized token pour le placer dans la session / protège des attaques csrf en mode statefull
        http.cors().disable();


        http.authorizeRequests().antMatchers(HttpMethod.GET,"/cities/**","/hotels/**","/rooms/**").permitAll();

        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/rooms/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/cities/**","/hotels/**").hasAuthority("SUPERVISOR");


        //Gérer les accès en fonction des rôles
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/users/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/users/**").hasAuthority("SUPERVISOR");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/login/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/order/**").hasAuthority("USER");

        http.authorizeRequests().antMatchers(HttpMethod.GET,"/rooms/photo/**","/hotels/photo/**","/cities/photo/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/rooms/photo/**","/hotels/photo/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/cities/photo/**").hasAuthority("SUPERVISOR");

        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/rooms/**","/hotels/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/cities/**").hasAuthority("SUPERVISOR");

        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

        http.authorizeRequests().anyRequest().authenticated();  //toutes les ressources nécessitent une authentification

        //Désactiver l'authentification basée sur les sessions -> demander à Spring d'utiliser le mode stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //ajout de notre filtre d'authentification
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));

        //ajout de notre filtre d'authorisation qui sera appelé le 1er pour vérifier le token...
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
