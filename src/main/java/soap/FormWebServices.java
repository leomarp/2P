package soap;

import com.google.gson.Gson;
import encapsulaciones.Formulario;
import encapsulaciones.Foto;
import encapsulaciones.Ubicacion;
import encapsulaciones.Usuario;
import services.FormularioServices;
import services.FotoServices;
import services.UbicacionServices;
import services.UsuarioServices;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Clase para implementar un servicio web basado en SOAP
 */

@WebService
public class FormWebServices {

    @WebMethod
    public String holaMundo(String hola){
        System.out.println("Ejecuntado en el servidor.");
        return "Hola Mundo "+hola+", :-D";
    }

    @WebMethod
    public String listarForms(String user){

        Usuario usuario = UsuarioServices.getInstancia().find(user);
        EntityManager em = FormularioServices.getInstancia().getEntityManager();

        String queryString = "SELECT u FROM Ubicacion u " +
                "WHERE u.formulario.usuario.username = :username";

        Query query = em.createQuery(queryString);

        query.setParameter("username", usuario.getUsername());

        List<Formulario> lista = query.getResultList();

        Gson g = new Gson();

        return g.toJson(lista);

    }

    @WebMethod
    public void newForm(String form, String location, String foto){

        Gson g1 = new Gson();
        Gson g2 = new Gson();
        Gson g3 = new Gson();

        Foto fotografia = new Foto();
        fotografia = g3.fromJson(foto, Foto.class);
        Formulario formulario = new Formulario();
        formulario = g1.fromJson(form, Formulario.class);
        formulario.setFoto(fotografia);
        Ubicacion ubicacion = new Ubicacion();
        ubicacion = g2.fromJson(location, Ubicacion.class);
        ubicacion.setFormulario(formulario);

        FotoServices.getInstancia().crear(fotografia);
        FormularioServices.getInstancia().crear(formulario);
        UbicacionServices.getInstancia().crear(ubicacion);

    }


}
