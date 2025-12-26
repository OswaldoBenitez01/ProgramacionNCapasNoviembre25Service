
package OBenitez.ProgramacionNCapasNoviembre25.RestController;

import OBenitez.ProgramacionNCapasNoviembre25.DAO.PaisJPADAOImplementation;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pais")
public class PaisRestController {
    @Autowired
    private PaisJPADAOImplementation paisJPADAOImplementaion;
    
    @GetMapping
    public ResponseEntity GetAll(){
        Result result = paisJPADAOImplementaion.GetAll();
        return ResponseEntity.status(result.StatusCode).body(result);
    }
}
