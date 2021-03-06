package br.com.wepdev.oauth.services;

import br.com.wepdev.oauth.entities.User;
import br.com.wepdev.oauth.feignclients.UsuarioFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {


    @Autowired
    private UsuarioFeignClient usuarioFeignClient;


    // static so pode ser instanciado apenas 1 Logger
    private static Logger logger = LoggerFactory.getLogger(UsuarioService.class); // para teste, resultado impresso no console



    public User findByEmail(String email){
        User user = usuarioFeignClient.findbyEmail(email).getBody();

        if(user == null){
            logger.error("Email não existe : " + email);
            throw new IllegalArgumentException("Email não existe");
        }
        logger.info("Email encontrado : " + email);
        return user;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usuarioFeignClient.findbyEmail(username).getBody();

        if(user == null){
            logger.error("Email não existe : " + username);
            throw new UsernameNotFoundException("Email não existe");
        }
        logger.info("Email encontrado : " + username);
        return user;
    }
}
