
package OBenitez.ProgramacionNCapasNoviembre25.RestController;

import OBenitez.ProgramacionNCapasNoviembre25.DAO.UsuarioJPADAOImplementation;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController {
    
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    
    @GetMapping
    public ResponseEntity GetAll(){
        Result result = usuarioJPADAOImplementation.GetAll();
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @GetMapping("/{IdUsuario}")
    public ResponseEntity GetById(@PathVariable int IdUsuario){
        Result result = usuarioJPADAOImplementation.GetById(IdUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @PostMapping("/busqueda")
    public ResponseEntity BusquedaAbierta(@ModelAttribute Usuario usuario){
        Result result = usuarioJPADAOImplementation.BusquedaUser(usuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @PostMapping("/add")
    public ResponseEntity Add(@ModelAttribute Usuario usuario){
        Result result = usuarioJPADAOImplementation.Add(usuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @PutMapping
    public ResponseEntity UpdateUser(@ModelAttribute Usuario usuario){
        Result result = usuarioJPADAOImplementation.UpdateUser(usuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @PatchMapping("/{IdUsuario}")
    public ResponseEntity ToggleStatus(@PathVariable int IdUsuario, @RequestParam("status") int Status){
        Result result = usuarioJPADAOImplementation.UpdateStatusById(IdUsuario, Status);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @PostMapping("/updatePhoto")
    public ResponseEntity UpdatePhoto(@ModelAttribute Usuario usuario, @PathVariable("imagenUsuario") MultipartFile imagenUsuario) {
        Result result = usuarioJPADAOImplementation.UpdatePhoto(usuario.getIdUsuario(), imagenUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @DeleteMapping("/{IdUsuario}")
    public ResponseEntity DeleteUser(@PathVariable("IdUsuario") int IdUsuario){
        Result result = usuarioJPADAOImplementation.DeleteUserById(IdUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
}
