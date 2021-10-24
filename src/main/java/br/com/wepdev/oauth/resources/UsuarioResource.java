package br.com.wepdev.oauth.resources;


import br.com.wepdev.oauth.entities.Usuario;
import br.com.wepdev.oauth.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {


    @Autowired
    private UsuarioService usuarioService;




    @GetMapping(value = "/search")
    public ResponseEntity<Usuario> findByEmail(@RequestParam String email){
        try {
            Usuario usuario = usuarioService.findByEmail(email);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }


    }
}
