package OBenitez.ProgramacionNCapasNoviembre25.Service;

import OBenitez.ProgramacionNCapasNoviembre25.DAO.IColonia;
import OBenitez.ProgramacionNCapasNoviembre25.DAO.IDireccion;
import OBenitez.ProgramacionNCapasNoviembre25.DAO.IUsuarioJPA;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Colonia;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Direccion;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service
public class DireccionService {
    @Autowired
    private IColonia coloniaRepository;
    @Autowired
    private IDireccion direccionRepository;
    @Autowired
    private IUsuarioJPA usuarioRepository;
    
    public Result AddAddress(Direccion direccion) {
        Result result = new Result();
        
        try {
            // Validar estructura básica
            if (direccion == null || direccion.usuario == null || direccion.usuario.getIdUsuario() == null) {
                result.Correct = false;
                result.ErrorMessage = "La dirección debe incluir un usuario válido";
                result.StatusCode = 400;
                return result;
            }
            
            // Validar que el usuario existe
            Optional<Usuario> usuarioDB = usuarioRepository.findById(direccion.usuario.getIdUsuario());
            if (usuarioDB.isEmpty()) {
                result.Correct = false;
                result.ErrorMessage = "Usuario no encontrado";
                result.StatusCode = 404;
                return result;
            }
            
            // Validar y asignar colonia si existe
            if (direccion.colonia != null && direccion.colonia.getIdColonia() != 0) {
                Optional<Colonia> coloniaDB = coloniaRepository.findById(direccion.colonia.getIdColonia());
                if (coloniaDB.isPresent()) {
                    direccion.setColonia(coloniaDB.get());
                }
            }
            
            direccion.setUsuario(usuarioDB.get());
            direccionRepository.save(direccion);
            
            result.Object = direccion;
            result.Correct = true;
            result.StatusCode = 201;
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = "Error interno del servidor: " + ex.getLocalizedMessage();
            result.ex = ex;
            result.StatusCode = 500;
        }
        return result;
    }
    
    public Result UpdateAddressById(Direccion direccion) {
        Result result = new Result();

        try {
            // Validar ID de dirección
            if (direccion == null || direccion.getIdDireccion() == 0) {
                result.Correct = false;
                result.ErrorMessage = "Debe proporcionar un ID de dirección válido";
                result.StatusCode = 400;
                return result;
            }


            // Buscar dirección existente
            Optional<Direccion> direccionDB = direccionRepository.findById(direccion.getIdDireccion());
            if (direccionDB.isEmpty()) {
                result.Correct = false;
                result.ErrorMessage = "Dirección no encontrada";
                result.StatusCode = 404;
                return result;
            }

            // Actualizar campos
            direccionDB.get().setCalle(direccion.getCalle());
            direccionDB.get().setNumeroInterior(direccion.getNumeroInterior());
            direccionDB.get().setNumeroExterior(direccion.getNumeroExterior());

            // Actualizar colonia si viene en el request
            if (direccion.getColonia() != null && direccion.getColonia().getIdColonia() != 0) {
                Optional<Colonia> coloniaDB = coloniaRepository.findById(
                    direccion.getColonia().getIdColonia()
                );
                if (coloniaDB.isPresent()) {
                    direccionDB.get().setColonia(coloniaDB.get());
                }
            }

            direccionRepository.save(direccionDB.get());

            result.Object = direccionDB.get();
            result.Correct = true;
            result.StatusCode = 200;

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = "Error interno del servidor: " + ex.getLocalizedMessage();
            result.ex = ex;
            result.StatusCode = 500;
        }
        return result;
    }

    public Result DeleteAddressById(int idDireccion) {
        Result result = new Result();
        
        try {
            // Validar ID
            if (idDireccion <= 0) {
                result.Correct = false;
                result.ErrorMessage = "Debe proporcionar un ID de dirección válido";
                result.StatusCode = 400;
                return result;
            }
            
            // Buscar dirección existente
            Optional<Direccion> direccionDB = direccionRepository.findById(idDireccion);
            if (direccionDB.isEmpty()) {
                result.Correct = false;
                result.ErrorMessage = "Dirección no encontrada";
                result.StatusCode = 404;
                return result;
            }
            
            // Eliminar
            direccionRepository.deleteById(idDireccion);
            
            result.Correct = true;
            result.StatusCode = 200;
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = "Error interno del servidor: " + ex.getLocalizedMessage();
            result.ex = ex;
            result.StatusCode = 500;
        }
        return result;
    }
}

