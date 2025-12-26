package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Colonia;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Direccion;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionJPADAOImplementation implements IDireccion{
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public Result AddAddress(Direccion direccion) {
        Result result = new Result();
        
        try {  
            if (direccion == null || direccion.Usuario == null || direccion.Usuario.getIdUsuario() == null) {
                result.Correct = false;
                result.ErrorMessage = "La direccion debe incluir un usuario valido";
                result.StatusCode = 400;
            }
            
            Usuario usuarioDB = entityManager.find(Usuario.class, direccion.Usuario.getIdUsuario());
            if (usuarioDB == null) {
                result.Correct = false;
                result.ErrorMessage = "Usuario no encontrado";
                result.StatusCode = 404;
                return result;
            }
            
            if (direccion.Colonia != null && direccion.Colonia.getIdColonia() != 0) {
                Colonia coloniaDB = entityManager.find(Colonia.class, direccion.Colonia.getIdColonia());
                direccion.setColonia(coloniaDB);
            }
            
            direccion.setUsuario(usuarioDB);
            entityManager.persist(direccion);
            
            result.Correct = true;
            result.StatusCode = 201;
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.StatusCode = 500;
        }
        
        return result;
    }

    @Override
    @Transactional
    public Result UpdateAddressById(Direccion direccion) {
        Result result = new Result();
        
        try {
            Direccion direccionDB = entityManager.find(Direccion.class, direccion.getIdDireccion());
            
            if (direccionDB == null) {
                result.Correct = false;
                result.ErrorMessage = "Direccion no encontrada";
                result.StatusCode = 404;
                return result;
            }
            
            direccionDB.setCalle(direccion.getCalle());
            direccionDB.setNumeroInterior(direccion.getNumeroInterior());
            direccionDB.setNumeroExterior(direccion.getNumeroExterior());
            if (direccion.Colonia != null && direccion.Colonia.getIdColonia() != 0) {
                Colonia coloniaDB = entityManager.find(Colonia.class, direccion.Colonia.getIdColonia());
                direccionDB.setColonia(coloniaDB);
            }
            
            entityManager.merge(direccionDB);
            
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

    @Override
    @Transactional
    public Result DeleteAddressById(int IdDireccion) {
        Result result = new Result();
        try {
            Direccion direccionDB = entityManager.find(Direccion.class, IdDireccion);
            
            if (direccionDB == null) {
                result.Correct = false;
                result.ErrorMessage = "Direccion no encontrado";
                result.StatusCode = 404;
                return result;
            }
            
            entityManager.remove(direccionDB);
            
            result.Correct = true;
            result.StatusCode = 204;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.StatusCode = 500;
        }
        
        return result;
    }
}
