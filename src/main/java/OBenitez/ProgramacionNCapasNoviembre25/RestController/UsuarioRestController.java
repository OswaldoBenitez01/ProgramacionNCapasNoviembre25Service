
package OBenitez.ProgramacionNCapasNoviembre25.RestController;

import OBenitez.ProgramacionNCapasNoviembre25.DAO.UsuarioJPADAOImplementation;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Colonia;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Direccion;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Result;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Rol;
import OBenitez.ProgramacionNCapasNoviembre25.JPA.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/usuario")
@Tag(name = "API de Usuario")
public class UsuarioRestController {
    
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    
    @GetMapping
    @Operation(
            summary = "Extraer usuarios", 
            description = "Extrae un listado de cada usuario de la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "Usuarios encontrados",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Usuario.class))
                    )),
        @ApiResponse(responseCode = "404", 
                    description = "No se encontraron usuarios",
                    content = @Content()),
        @ApiResponse(responseCode = "500", 
                    description = "Error en la solicitud", 
                    content = @Content())
    })
    public ResponseEntity GetAll(){
        Result result = usuarioJPADAOImplementation.GetAll();
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @GetMapping("/{IdUsuario}")
    @Operation(
            summary = "Extraer detalle de usuario", 
            description = "Extrae informacion detallada de un usuario en especifico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "Usuario encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class)
                    )),
        @ApiResponse(responseCode = "404", 
                    description = "Usuario no encontrado",
                    content = @Content()),
        @ApiResponse(responseCode = "500", 
                    description = "Error en la solicitud", 
                    content = @Content())
    })
    public ResponseEntity GetById(@PathVariable @Parameter(description = "Id del usuario", example = "1") int IdUsuario ){
        Result result = usuarioJPADAOImplementation.GetById(IdUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @PostMapping("/busqueda")
    @Operation(
            summary = "Busqueda de usuarios", 
            description = "Realiza una busqueda de usuarios por medio de parametros"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "Usuarios encontrados",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Usuario.class))
                    )),
        @ApiResponse(responseCode = "404", 
                    description = "No se encontraron usuarios",
                    content = @Content()),
        @ApiResponse(responseCode = "500", 
                    description = "Error en la solicitud", 
                    content = @Content())
    })
    public ResponseEntity BusquedaAbierta(@RequestBody Usuario usuario){
        Result result = usuarioJPADAOImplementation.BusquedaUser(usuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @PostMapping
    public ResponseEntity Add(@RequestBody Usuario usuario){
        Result result = usuarioJPADAOImplementation.Add(usuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @PutMapping
    public ResponseEntity UpdateUser(@RequestBody Usuario usuario){
        Result result = usuarioJPADAOImplementation.UpdateUser(usuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @PatchMapping("/{IdUsuario}")
    public ResponseEntity ToggleStatus(@PathVariable int IdUsuario, @RequestParam("status") int Status){
        Result result = usuarioJPADAOImplementation.UpdateStatusById(IdUsuario, Status);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @PostMapping("/{IdUsuario}/updatePhoto")
    public ResponseEntity UpdatePhoto(@PathVariable int IdUsuario, @RequestBody String imagenUsuario) {
        Result result = usuarioJPADAOImplementation.UpdatePhoto(IdUsuario, imagenUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @DeleteMapping("/{IdUsuario}/photo")
    public ResponseEntity<Result> deletePhoto(@PathVariable int IdUsuario) {
        Result result = usuarioJPADAOImplementation.DeletePhoto(IdUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @DeleteMapping("/{IdUsuario}")
    public ResponseEntity DeleteUser(@PathVariable("IdUsuario") int IdUsuario){
        Result result = usuarioJPADAOImplementation.DeleteUserById(IdUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @PostMapping("/cargaMasiva/validar")
    public ResponseEntity<Result> validarCargaMasiva(@RequestParam("file") MultipartFile file) {
        Result result = new Result();
        
        try {
            if (file.isEmpty()) {
                result.Correct = false;
                result.ErrorMessage = "El archivo esta vacio";
                result.StatusCode = 400;
                return ResponseEntity.status(result.StatusCode).body(result);
            }
            
            String token = UUID.randomUUID().toString();
            String extension = file.getOriginalFilename().split("\\.")[1];
            
            String path = System.getProperty("user.dir");
            String pathArchivo = "src/main/resources/archivos/";
            String rutaAbsoluta = path + "/" + pathArchivo;
            
            String nombreArchivo = token + "." + extension;
            File destino = new File(rutaAbsoluta + nombreArchivo);
            file.transferTo(destino);
            
            List<Usuario> usuarios = new ArrayList<>();
        
            if (extension.equals("txt")) {
                usuarios = LecturaArchivo(destino);
            } else {
                usuarios = LecturaArchivoExcel(destino);    
            }
            
            if (usuarios.isEmpty()) {
                result.Correct = false;
                result.ErrorMessage = "No se encontraron registros en el archivo";
                result.StatusCode = 400;
                return ResponseEntity.status(result.StatusCode).body(result);
            }
            
            result.Correct = true;
            result.Object = token;
            result.ErrorMessage = "Se encontraton " + usuarios.size() + " registros";
            result.StatusCode = 200;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.StatusCode = 500;
        }
        
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    
    @PostMapping("/cargaMasiva/procesar/{token}")
    public ResponseEntity<Result> procesarCargaMasiva(@PathVariable String token){
    
        Result result = new Result();
        try {
            String path = System.getProperty("user.dir");
            String pathArchivo = "src/main/resources/archivos/";
            String rutaAbsoluta = path + "/" + pathArchivo;
            
            File directorio = new File(rutaAbsoluta);
            File[] todosLosArchivos = directorio.listFiles();
            
            File archivo = null;
            if (todosLosArchivos != null) {
                for (File archivoActual : todosLosArchivos) {
                    if (archivoActual.getName().startsWith(token)) {
                        archivo = archivoActual;
                        break;
                    }
                }
            }
            
            if (archivo == null) {
                result.Correct = false;
                result.ErrorMessage = "Archivo no encontrado";
                result.StatusCode = 404;
                return ResponseEntity.status(result.StatusCode).body(result);
            }
            
            String nombreArchivo = archivo.getName();
            String extension = nombreArchivo.split("\\.")[1];
            List<Usuario> usuarios = new ArrayList<>();

            if (extension.equals("txt")) {
                usuarios = LecturaArchivo(archivo);
            } else {
                usuarios = LecturaArchivoExcel(archivo);  
            }
            
            if (usuarios == null || usuarios.isEmpty()) {
                result.Correct = false;
                result.ErrorMessage = "No se pudieron leer los aregistros del archivo";
                result.StatusCode = 400;
                return ResponseEntity.status(result.StatusCode).body(result);
            }
            
            //result = usuarioJPADAOImplementation.AddAll(usuarios);
            result.Correct = true;
            if (result.Correct) {
                result.Object = "Se agregaron " + usuarios.size() + " usuarios";
                result.StatusCode = 200;
            } else {
                result.Object = "No fue posible agregar a los usuarios";
                result.StatusCode = 400;
            }
        } catch (Exception ex) {
            result.Correct = true;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.StatusCode = 500;
        }
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    private List<Usuario> LecturaArchivo(File archivo) {
        
        List<Usuario> usuarios = new ArrayList<>();
        
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo))){
            
            bufferedReader.readLine();
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {                
                
                String[] datos = line.split("\\|");
                
                Usuario usuario = new Usuario();
                usuario.setUsername(datos[0]);
                usuario.setNombre(datos[1]);
                usuario.setApellidoPaterno(datos[2]);
                usuario.setApellidoMaterno(datos[3]);
                usuario.setEmail(datos[4]);
                usuario.setPassword(datos[5]);
                usuario.setFechaNacimiento(java.sql.Date.valueOf(datos[6]));
                usuario.setSexo(datos[7]);
                usuario.setTelefono(datos[8]);
                usuario.setCelular(datos[9]);
                usuario.setCurp(datos[10]);
                
                //Direccion
                usuario.Rol = new Rol();
                usuario.Rol.setIdRol(Integer.parseInt(datos[11]));
                
                //DIRECCION
                usuario.Direcciones = new ArrayList<>();
                Direccion Direccion = new Direccion();
                Direccion.setCalle(datos[12]);
                Direccion.setNumeroExterior(datos[13]);
                Direccion.setNumeroInterior(datos[14]);
                usuario.Direcciones.add(Direccion);
                
                Direccion.Colonia = new Colonia();
                Direccion.Colonia.setIdColonia(Integer.parseInt(datos[15]));
                
                usuarios.add(usuario);
            }
        }
        catch(Exception ex){
            usuarios = null;
        }
        
        return usuarios;
    }

    private List<Usuario> LecturaArchivoExcel(File archivo) {
        
        List<Usuario> usuarios = new ArrayList<>();
        
         try (XSSFWorkbook workbook = new XSSFWorkbook(archivo)) {
             
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                
                Usuario usuario = new Usuario();
                Cell cell0 = row.getCell(0);
                if (cell0 != null) {
                    usuario.setUsername(row.getCell(0).toString());
                } else {
                    continue;
                }
                
                usuario.setNombre(row.getCell(1).toString());
                usuario.setApellidoPaterno(row.getCell(2).toString());
                usuario.setApellidoMaterno(row.getCell(3).toString());
                usuario.setEmail(row.getCell(4).toString());
                usuario.setPassword(row.getCell(5).toString());
                
                java.util.Date utilDate = row.getCell(6).getDateCellValue();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                usuario.setFechaNacimiento(sqlDate);
                
                usuario.setSexo(row.getCell(7).toString());
                usuario.setCelular(row.getCell(8).toString());
                usuario.setTelefono(row.getCell(9).toString());
                usuario.setCurp(row.getCell(10).toString());
                
                usuario.Rol = new Rol();
                usuario.Rol.setIdRol((int) row.getCell(11).getNumericCellValue());
                //DIRECCION
                usuario.Direcciones = new ArrayList<>();
                Direccion Direccion = new Direccion();
                Direccion.setCalle(row.getCell(12).toString());
                Direccion.setNumeroExterior(row.getCell(13).toString());
                Direccion.setNumeroInterior(row.getCell(14).toString());
                usuario.Direcciones.add(Direccion);
                
                Direccion.Colonia = new Colonia();
                Direccion.Colonia.setIdColonia((int) row.getCell(15).getNumericCellValue());
                
                usuarios.add(usuario);
            }
            
        } catch (Exception ex) {
            usuarios = null;
        }
        
        return usuarios;
    }
    
    
}
