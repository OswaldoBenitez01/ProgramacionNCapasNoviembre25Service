package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolJPADAOImplementation implements IRol{
    
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            
            TypedQuery<Rol> query = entityManager.createQuery("FROM Rol ORDER BY IdRol ASC", Rol.class);
            List<Rol> rolesJPA = query.getResultList();
            
            if (rolesJPA.isEmpty()) {
                result.Correct = false;
                result.ErrorMessage = "No se encontraron roles";
                result.StatusCode = 404;
            } else {
                result.Objects = new ArrayList<>();
                result.Objects.addAll(rolesJPA);
                result.Correct = true;
                result.StatusCode = 200;
            }
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.StatusCode = 500;
        }
        
        return result;
    }
    
}
