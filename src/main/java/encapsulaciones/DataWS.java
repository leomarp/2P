package encapsulaciones;

public class DataWS {
    private String longitud;
    private String latitud;
    private String nombre;
    private String apellido;
    private String provincia;
    private String nivelacad;
    private String usuario;
    private String foto;

    public DataWS(String longitud, String latitud, String nombre, String apellido, String provincia, String nivelacad, String usuario, String foto) {
        this.longitud = longitud;
        this.latitud = latitud;
        this.nombre = nombre;
        this.apellido = apellido;
        this.provincia = provincia;
        this.nivelacad = nivelacad;
        this.usuario = usuario;
        this.foto = foto;
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

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getNivelacad() {
        return nivelacad;
    }

    public void setNivelacad(String nivelacad) {
        this.nivelacad = nivelacad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
