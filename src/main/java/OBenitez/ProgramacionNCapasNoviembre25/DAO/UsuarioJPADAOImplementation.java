
package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Colonia;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Direccion;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA{

    @Autowired
    private EntityManager entityManager;
    
    ///GET Y BUSQUEDA
    @Override
    public Result GetAll() {
        
        Result result = new Result();
        try {
            
            TypedQuery<Usuario> query = entityManager.createQuery("FROM Usuario ORDER BY IdUsuario ASC", Usuario.class);
            List<Usuario> usuariosJPA = query.getResultList();
            
            if (usuariosJPA.isEmpty()) {
                result.Correct = false;
                result.ErrorMessage = "No se encontraron usuarios";
                result.StatusCode = 404;
                result.Objects = new ArrayList<>();
                return result;
            }
            
            result.Objects = new ArrayList<>(usuariosJPA);
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
    public Result GetById(int IdUsuario) {
        Result result = new Result();
        
        try {
            Usuario usuarioDB = entityManager.find(Usuario.class, IdUsuario);
            
            if (usuarioDB == null) {
                result.Correct = false;
                result.ErrorMessage = "Usuario no encontrado";
                result.StatusCode = 404;
                return result;
            } 
            
            result.Object = usuarioDB;
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
    public Result BusquedaUser(Usuario usuario) {
        Result result = new Result();
        
        try {
            StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("BusquedaUserWithAddress", Usuario.class)
                .registerStoredProcedureParameter("pCursor", void.class, ParameterMode.REF_CURSOR)
                .registerStoredProcedureParameter("pNombre", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("pApellidoPaterno", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("pApellidoMaterno", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("pIdRol", Integer.class, ParameterMode.IN);

            query.setParameter("pNombre", usuario.getNombre());
            query.setParameter("pApellidoPaterno", usuario.getApellidoPaterno());
            query.setParameter("pApellidoMaterno", usuario.getApellidoMaterno());
            Integer idRol = (usuario.Rol != null && usuario.Rol.getIdRol() != null) ? usuario.Rol.getIdRol() : -1;
            query.setParameter("pIdRol", idRol);

            List<Usuario> usuariosJPA = query.getResultList();

            if (usuariosJPA.isEmpty()) {
                result.Correct = false;
                result.ErrorMessage = "No se encontraron usuarios";
                result.StatusCode = 404;
                result.Objects = new ArrayList<>();
                return result;
            }
            
            result.Objects = new ArrayList<>(usuariosJPA);
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
    
    /////ADDS
    @Override
    @Transactional
    public Result Add(Usuario usuario) {
        Result result = new Result();
        usuario.setIdUsuario(null); //Para qeu no choque con el id 
        try {
            if (usuario.getDirecciones() != null && !usuario.getDirecciones().isEmpty()) {
                for (Direccion direccion : usuario.getDirecciones()) {
                    direccion.setUsuario(usuario);
                    if (direccion.Colonia != null && direccion.Colonia.getIdColonia() != 0) {
                        Colonia coloniadb = entityManager.find(Colonia.class, direccion.Colonia.getIdColonia());
                        direccion.setColonia(coloniadb);
                    }
                }
            }
            entityManager.persist(usuario);
            entityManager.flush();
            
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
    public Result AddAll(List<Usuario> usuarios) {
        Result result = new Result();
        
        try {
            
            for (Usuario usuario : usuarios) {
                entityManager.persist(usuario);
                entityManager.flush();
//
//                OBenitez.ProgramacionNCapasNoviembre25.JPA.Direccion direccion = new OBenitez.ProgramacionNCapasNoviembre25.JPA.Direccion();
//                direccion.setUsuario(usuario);
//                direccion.setCalle(usuario.getDirecciones().get(0).getCalle());
//                direccion.setNumeroInterior(usuario.getDirecciones().get(0).getNumeroInterior());
//                direccion.setNumeroExterior(usuario.getDirecciones().get(0).getNumeroExterior());
//                direccion.setColonia(usuario.getDirecciones().get(0).getColonia());
//
//                entityManager.persist(direccion);
            }
            
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    /////UPDATES
    @Override
    @Transactional
    public Result UpdateUser(Usuario usuario) {
        Result result = new Result();
        
        try {
            Usuario usuarioDB = entityManager.find(Usuario.class, usuario.getIdUsuario());
            
            if (usuarioDB == null) {
                result.Correct = false;
                result.ErrorMessage = "Usuario no encontrado";
                result.StatusCode = 404;
                return result;
            }

            usuario.Direcciones = usuarioDB.Direcciones;
            usuario.setImagen(usuarioDB.getImagen());
            entityManager.merge(usuario);
            
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
    public Result UpdateStatusById(Integer IdUsuario, Integer status) {
        Result result = new Result();
        
        try {
            Usuario usuarioDB = entityManager.find(Usuario.class, IdUsuario);
            
            if (usuarioDB == null) {
                result.Correct = false;
                result.ErrorMessage = "No se encontro al usuario";
                result.StatusCode = 404;
                return result;
            }
            
            usuarioDB.setStatus(status);
            entityManager.merge(usuarioDB);
            
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
    public Result UpdatePhoto(Integer IdUsuario, String imagenUsuario) {
        Result result = new Result();
        try {
            Usuario usuarioDB = entityManager.find(Usuario.class, IdUsuario);
            if (usuarioDB == null) {
                result.Correct = false;
                result.ErrorMessage = "No se encontro al usuario";
                result.StatusCode = 404;
                return result;
            }
            
            usuarioDB.setImagen(imagenUsuario);
            entityManager.merge(usuarioDB);
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
    
    /////DELETES
    @Override
    @Transactional
    public Result DeleteUserById(int IdUsuario) {
        Result result = new Result();
        
        try {
            Usuario usuarioDB = entityManager.find(Usuario.class, IdUsuario);
            
            if (usuarioDB == null) {
                result.Correct = false;
                result.ErrorMessage = "Usuario no encontrado";
                result.StatusCode = 404;
                return result;
            }
            
            entityManager.remove(usuarioDB);
            
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
    
    @Override
    @Transactional
    public Result DeletePhoto(Integer IdUsuario) {
        Result result = new Result();
        try {
            Usuario usuarioDB = entityManager.find(Usuario.class, IdUsuario);
            if (usuarioDB == null) {
                result.Correct = false;
                result.ErrorMessage = "Usuario no encontrado";
                result.StatusCode = 404;
                return result;
            }

            usuarioDB.setImagen(null);
            entityManager.merge(usuarioDB);

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
