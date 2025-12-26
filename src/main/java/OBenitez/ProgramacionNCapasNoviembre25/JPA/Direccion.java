
package OBenitez.ProgramacionNCapasNoviembre25.JPA;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DIRECCION")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddireccion")
    private int IdDireccion;
    
    @Column(name = "calle")
    private String Calle;
    
    @Column(name = "numerointerior")
    private String NumeroInterior;
    
    @Column(name = "numeroexterior")
    private String NumeroExterior;
    
    @ManyToOne
    @JoinColumn(name = "idcolonia")
    @JsonIgnore
    public Colonia Colonia;
    
    @ManyToOne
    @JoinColumn(name = "idusuario")
    @JsonIgnore
    public Usuario Usuario;
    
    //GETTERS Y SETTERS

    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }  

    @JsonIgnore
    public Usuario getUsuario() {
        return Usuario;
    }
    @JsonProperty
    public void setUsuario(Usuario Usuario) {
        this.Usuario = Usuario;
    }
    
}
