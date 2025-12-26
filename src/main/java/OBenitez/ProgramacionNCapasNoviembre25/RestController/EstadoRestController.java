package OBenitez.ProgramacionNCapasNoviembre25.RestController;

import OBenitez.ProgramacionNCapasNoviembre25.DAO.EstadoJPADAOImplementation;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/estado")
public class EstadoRestController {
    @Autowired
    private EstadoJPADAOImplementation estadoJPADAOImplementation;
    
    @GetMapping("pais/{IdPais}")
    public ResponseEntity GetEstadosByPais(@PathVariable int IdPais){
        Result result = estadoJPADAOImplementation.GetEstadosByPais(IdPais);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
}
