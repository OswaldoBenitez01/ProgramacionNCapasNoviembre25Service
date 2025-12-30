
package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Direccion;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Usuario;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IUsuarioJPA {
    //GET Y SEARCH
    public Result GetAll();
    public Result GetById(int IdUsuario);
    public Result BusquedaUser(Usuario usuario);
//    //ADDS
    public Result Add(Usuario usuario);
//    public Result AddAll(List<Usuario> usuarios);
//    //UPDATES
    public Result UpdateUser(Usuario usuario);
    public Result UpdateStatusById(Integer IdUsuario, Integer status);
    public Result UpdatePhoto(Integer IdUsuario, String imagenUsuario);
//    //DELETES
    public Result DeleteUserById(int IdUsuario);
    public Result DeletePhoto(Integer IdUsuario);
}
