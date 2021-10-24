package br.com.wepdev.oauth.services;

import br.com.wepdev.oauth.entities.Usuario;
import br.com.wepdev.oauth.feignclients.UsuarioFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {


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
}
