package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Municipio;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioJPADAOImplementation implements IMunicipio{
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetMunicipiosByEstado(int idEstado) {
        Result result = new Result();
        
        try {
            
            TypedQuery<Municipio> query = entityManager
                    .createQuery("SELECT m FROM Municipio m WHERE m.Estado.IdEstado = :idestado ORDER BY IdMunicipio ASC",Municipio.class);
            query.setParameter("idestado", idEstado);
            List<Municipio> municipiosJPA = query.getResultList();
            
            if (municipiosJPA.isEmpty()) {
                result.Correct = false;
                result.ErrorMessage = "No se encontraron municipios";
                result.StatusCode = 404;
                result.Objects = new ArrayList<>();
                return result;
            } 
            
            result.Objects = new ArrayList<>(municipiosJPA);
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
