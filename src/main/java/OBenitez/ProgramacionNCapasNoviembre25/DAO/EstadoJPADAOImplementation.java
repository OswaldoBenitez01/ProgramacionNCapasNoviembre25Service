package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Estado;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoJPADAOImplementation implements IEstado{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetEstadosByPais(int IdPais) {
        Result result = new Result();
        
        try {
            
            TypedQuery<Estado> query = entityManager
                    .createQuery("SELECT e FROM Estado e WHERE e.Pais.IdPais = :idpais ORDER BY IdEstado ASC",Estado.class);
            query.setParameter("idpais", IdPais);
            List<Estado> estadosJPA = query.getResultList();
            
            if (estadosJPA.isEmpty()) {
                result.Correct = false;
                result.ErrorMessage = "No se encontraron estados";
                result.StatusCode = 404;
                result.Objects = new ArrayList<>();
                return result;
            }
            
            result.Objects = new ArrayList<>(estadosJPA);
            result.Correct = true;
            result.StatusCode = 200;
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.StatusCode = 500;
        }
        return result;
    }
}
