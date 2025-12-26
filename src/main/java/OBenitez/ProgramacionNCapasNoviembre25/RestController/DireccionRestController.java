package OBenitez.ProgramacionNCapasNoviembre25.RestController;

import OBenitez.ProgramacionNCapasNoviembre25.DAO.DireccionJPADAOImplementation;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Direccion;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/direccion")
public class DireccionRestController {
    
    @Autowired
    private DireccionJPADAOImplementation direccionJPADAOImplementation;
    
    @PostMapping
    public ResponseEntity AddAddress(@ModelAttribute Direccion direccion){
        Result result = direccionJPADAOImplementation.AddAddress(direccion);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @DeleteMapping("/{IdAddress}")
    public ResponseEntity DeleteAddress(@PathVariable("IdAddress") int IdAddress){
        Result result = direccionJPADAOImplementation.DeleteAddressById(IdAddress);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @PutMapping
    public ResponseEntity UpdateAddress(@ModelAttribute Direccion direccion){
        Result result = direccionJPADAOImplementation.UpdateAddressById(direccion);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
}
