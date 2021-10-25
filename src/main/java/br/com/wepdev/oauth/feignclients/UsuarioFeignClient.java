package br.com.wepdev.oauth.feignclients;

import br.com.wepdev.oauth.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


// name = "usuario" -> nome do micro serviço no qual tera comunicação --- path = "/usuarios" -> caminho do recurso
@FeignClient(name = "usuario", path = "/usuarios")
@Component
public interface UsuarioFeignClient {


    /**
     * O parametro passado na URL é opicional.
     *
     * O parametro passado ficara mais ou menos assim : /search?email=nina@gmail.com
     */
    @GetMapping(value = "/search")
    ResponseEntity<User> findbyEmail(@RequestParam String email);


}
