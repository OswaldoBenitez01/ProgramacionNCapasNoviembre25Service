package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Colonia;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaJPADAOImplementation implements IColonia{
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetColoniasByMunicipio(int idMunicipio) {
        Result result = new Result();
        
        try {
            TypedQuery<Colonia> query = entityManager
                    .createQuery("SELECT c FROM Colonia c WHERE c.Municipio.IdMunicipio = :idmunicipio ORDER BY IdColonia ASC",Colonia.class);
            query.setParameter("idmunicipio", idMunicipio);
            List<Colonia> coloniasJPA = query.getResultList();
            
            if (coloniasJPA.isEmpty()) {
                result.Correct = false;
                result.ErrorMessage = "No se encontraron colonias";
                result.StatusCode = 404;
            } else {
                result.Objects = new ArrayList<>();
                result.Objects.addAll(coloniasJPA);
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
