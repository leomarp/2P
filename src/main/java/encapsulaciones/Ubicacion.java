package encapsulaciones;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Ubicacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String longitud;
    private String latitud;

    @OneToOne(fetch = FetchType.EAGER)
    private Formulario formulario;

    public Ubicacion() {}

    public Ubicacion(String longitud, String latitud, Formulario formulario) {
        this.longitud = longitud;
        this.latitud = latitud;
        this.formulario = formulario;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }
}
