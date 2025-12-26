
package OBenitez.ProgramacionNCapasNoviembre25.RestController;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class DemoRestController {
    ////PATH VARIABLE
    @GetMapping("suma/{numero1}/{numero2}")
    public int Suma(@PathVariable int numero1, @PathVariable int numero2){
        int resultado = numero1 + numero2;
        return resultado;
    }
    
    @GetMapping("resta/{numero1}/{numero2}")
    public int Resta(@PathVariable int numero1, @PathVariable int numero2){
        int resultado = numero1 - numero2;
        return resultado;
    }
    
    @GetMapping("multiplicacion/{numero1}/{numero2}")
    public int Multiplicacion(@PathVariable int numero1, @PathVariable int numero2){
        int resultado = numero1 * numero2;
        return resultado;
    }
    
    @GetMapping("division/{numero1}/{numero2}")
    public int Division(@PathVariable int numero1, @PathVariable int numero2){
        int resultado = numero1 / numero2;
        return resultado;
    }
    ////REQUEST PARAM
    @GetMapping("suma")
    public int SumaUrl(@RequestParam int numero1, @RequestParam int numero2){
        int resultado = numero1 + numero2;
        return resultado;
    }
    
    @GetMapping("resta")
    public int RestaUrl(@RequestParam int numero1, @RequestParam int numero2){
        int resultado = numero1 - numero2;
        return resultado;
    }
    
    @GetMapping("multiplicacion")
    public int MultiplicacionUrl(@RequestParam int numero1, @RequestParam int numero2){
        int resultado = numero1 * numero2;
        return resultado;
    }
    
    @GetMapping("division")
    public int DivisionUrl(@RequestParam int numero1, @RequestParam int numero2){
        int resultado = numero1 / numero2;
        return resultado;
    }
    
    @PostMapping("/saludo")
    public ResponseEntity SaludoUsuario(@RequestBody Usuario usuario) {
        
        if (usuario.getNombre() != null) {
            return ResponseEntity.ok("El usuario " + usuario.getNombre() + " ha sido agregado");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nombre no valido");
            //return new ResponseEntity("No hay nada que procesar :c", HttpStatus.NOT_FOUND);
            //return ResponseEntity.notFound().build();
        }
    }
}
