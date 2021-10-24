package br.com.wepdev.oauth.services;

import br.com.wepdev.oauth.entities.Usuario;
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



    public Usuario findByEmail(String email){
        Usuario usuario = usuarioFeignClient.findbyEmail(email).getBody();

        if(usuario == null){
            logger.error("Email não existe : " + email);
            throw new IllegalArgumentException("Email não existe");
        }
        logger.info("Email encontrado : " + email);
        return usuario;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioFeignClient.findbyEmail(username).getBody();

        if(usuario == null){
            logger.error("Email não existe : " + username);
            throw new UsernameNotFoundException("Email não existe");
        }
        logger.info("Email encontrado : " + username);
        return usuario;
    }
}
