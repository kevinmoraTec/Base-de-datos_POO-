package Modelo_Per;
/**
 *
 * @author danielmora
 */
import javax.swing.JTextField;

public class Docente {

    private String id, password;
    private String nombre, apellido;

    public Docente(String id, String password, String nombre, String apellido) {
        this.id = id;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

}
