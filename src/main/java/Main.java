import com.google.gson.Gson;
import com.sun.net.httpserver.HttpContext;
import encapsulaciones.*;
import io.javalin.Javalin;
import io.javalin.http.ForbiddenResponse;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.eclipse.jetty.http.spi.HttpSpiContextHandler;
import org.eclipse.jetty.http.spi.JettyHttpContext;
import org.eclipse.jetty.http.spi.JettyHttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import services.*;
//import soap.FormWebServices;

import javax.crypto.SecretKey;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.ws.Endpoint;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;
import static org.eclipse.jetty.http.HttpStatus.Code.FORBIDDEN;

public class Main {

    public final static String LLAVE_SECRETA = "asd12D1234dfr123@#4Fsdcasdd5g78a";

    public static void main(String[] args) {

        //JAVALIN CONFIG
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public"); //STATIC FILES -> /resources/public
            config.enableCorsForAllOrigins();
        });

        /**
         * SOAP API CONFIG BEFORE STARTING SERVER


        Server server = app.server().server();
        ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();
        server.setHandler(contextHandlerCollection);

        //Contexto donde estoy agrupando.
        try {
            HttpContext context = build(server, "/ws");

            //El o los servicios que estoy agrupando en ese contexto
            FormWebServices wsa = new FormWebServices();
            Endpoint endpoint = Endpoint.create(wsa);
            endpoint.publish(context);
            // Para acceder al wsdl en http://localhost:8000/ws/EstudianteWebServices?wsdl
        }catch (Exception ex){
            ex.printStackTrace();
        }*/


        //JAVALIN INIT ON PORT 8000
        app.start(8000);

        //REGISTER THYMELEAF IN JAVALIN
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");

        //DATABASE INIT
        DBStart.getInstancia().init();

        //DEFAULT USER
        Usuario admin = new Usuario("admin","admin","Administrador", "administrador");
        UsuarioServices.getInstancia().editar(admin);

        /**
         * CORS STUFF !
         */

        app.before("/*", ctx ->{
            ctx.header("Access-Control-Allow-Origin", "*");
        });

        app.options("/*", ctx -> {

            System.out.println("Entrando al metodo de options");
            String accessControlRequestHeaders = ctx.header("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                ctx.header("Access-Control-Allow-Headers",accessControlRequestHeaders);
            }

            String accessControlRequestMethod = ctx.header("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                ctx.header("Access-Control-Allow-Methods",accessControlRequestMethod);
            }

            ctx.status(200).result("OK");

        });

        /**
         * LOGIN
         */

        app.get("/", ctx -> {
            ctx.redirect("/form/index.html");
        });

        app.get("/login", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("msg","");
            ctx.render("public/login/index.html", modelo);
        });

        app.post("/login", ctx -> {

            String user = ctx.formParam("username");
            String pass = ctx.formParam("pass");

            if(UsuarioServices.getInstancia().verifyUser(pass,user) == null){
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("msg","Combinación de usuario y contraseña erroneo! Favor verificar.");

                ctx.render("public/login/index.html", modelo);
            }else{
                Usuario logged = UsuarioServices.getInstancia().find(user);
                ctx.cookie("loggedUser", logged.getUsername(),86400);
                ctx.cookie("loggedRole", logged.getRole(),86400);
                ctx.redirect("/form/index.html");
            }

        });

        app.get("/formulario", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("usuario", ctx.sessionAttribute("loggedUser"));
            ctx.render("public/form/index.html");
        });

        /**
         * WEBSOCKET CONFIG
         */

        app.ws("/sincronizarForms", ws -> {

            ws.onConnect(ctx -> {
                System.out.println("Conexión Iniciada - "+ctx.getSessionId());
            });

            ws.onMessage(ctx -> {
                System.out.println("Mensaje Recibido de "+ctx.getSessionId()+" ====== ");
                System.out.println("Mensaje: "+ctx.message());
                System.out.println("================================");

                Gson g = new Gson();
                DataWS dws = g.fromJson(ctx.message(), DataWS.class);

                String nombre = dws.getNombre()+" "+dws.getApellido();
                String latitud = dws.getLatitud();
                String longitud = dws.getLongitud();
                String provincia = dws.getProvincia();
                String nivelacad = dws.getNivelacad();
                String usuario = dws.getUsuario();
               // String foto = dws.getFoto();

               /* Foto FOTO = new Foto(nombre, foto.substring(5, 15), foto.substring(23));
                FotoServices.getInstancia().crear(FOTO); */

                Usuario Usuario = UsuarioServices.getInstancia().find(usuario);
                Formulario form = new Formulario(nombre,provincia,nivelacad,Usuario);
                FormularioServices.getInstancia().crear(form);

                Ubicacion ubicacion = new Ubicacion(longitud,latitud,form);
                UbicacionServices.getInstancia().crear(ubicacion);


            });

            ws.onBinaryMessage(ctx -> {
                System.out.println("Mensaje Recibido Binario "+ctx.getSessionId()+" ====== ");
                System.out.println("Mensaje: "+ctx.data().length);
                System.out.println("================================");
            });

            ws.onClose(ctx -> {
                System.out.println("Conexión Cerrada - "+ctx.getSessionId());
            });

            ws.onError(ctx -> {
                System.out.println(ctx.error());
                System.out.println("Ocurrió un error en el WS");
            });
        });

        /**
         * MAPS ENDPOINT
         */

        app.get("/mapas", ctx -> {
            Map<String, Object> modelo = new HashMap<>();

            List<Ubicacion> ubicaciones = UbicacionServices.getInstancia().findAll();
            Gson gson = new Gson();
            String jsonString = gson.toJson(ubicaciones);
            System.out.println(jsonString);

            modelo.put("ubicaciones", jsonString);

            ctx.render("public/mapas/index.html", modelo);
        });

        /**
         * NEW USERS
         */

        app.get("/registrar", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
//            Usuario user = UsuarioServices.getInstancia().find(ctx.sessionAttribute("loggedUser"));

            modelo.put("msg","");
            ctx.render("public/registrar/index.html", modelo);


        });

        app.post("/registrar", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            String nombre = ctx.formParam("name");
            String usuario = ctx.formParam("username");
            String contrasena = ctx.formParam("pass");
            String rol = ctx.formParam("rol");


            if(UsuarioServices.getInstancia().find(usuario) != null){
                modelo.put("msg", "ERROR: EXISTE USUARIO CON ESE NOMBRE DE USUARIO");
            }else{
                System.out.println("Entro aqui");
                modelo.put("msg", "");
                UsuarioServices.getInstancia().crear(new Usuario(usuario, contrasena,nombre, rol));
            }

            System.out.println(UsuarioServices.getInstancia().find(usuario).getNombre());



            ctx.render("public/registrar/index.html",modelo );
        });

        /**
         * SYNCED FORMS
         */

        app.get("/sincronizados", ctx -> {

            Map<String, Object> modelo = new HashMap<>();
            List<Formulario> forms = FormularioServices.getInstancia().findAll();
            modelo.put("forms",forms);
            ctx.render("public/sincronizados/index.html", modelo);

        });

        /**
         * API REST
         *

        app.routes(() ->{

            path("/apirest", () -> {

                before("/listar",ctx -> {

                    System.out.println("Analizando que exista el token");

                    //informacion para consultar en la trama.
                    String header = "Authorization";
                    String prefijo = "Bearer";

                    //Verificando si existe el header de autorizacion.
                    String headerAutentificacion = ctx.header(header);
                    System.out.println(headerAutentificacion);
                    if(headerAutentificacion == null || !headerAutentificacion.startsWith(prefijo)){
                        System.out.println("No hay");
                        throw new ForbiddenResponse("No tiene permiso para acceder al recurso");
                    }else{

                        //recuperando el token y validando
                        String tramaJwt = headerAutentificacion.replace(prefijo, "");
                        try {
                            Claims claims = Jwts.parser()
                                    .setSigningKey(Keys.hmacShaKeyFor(LLAVE_SECRETA.getBytes()))
                                    .parseClaimsJws(tramaJwt).getBody();
                            System.out.println("Mostrando el JWT recibido: " + claims.toString());
                        }catch (ExpiredJwtException | MalformedJwtException | SignatureException e){ //Excepciones comunes
                            throw new ForbiddenResponse(e.getMessage());
                        }

                        //En este punto puedo realizar validaciones en función a los permisos del usuario.
                        //tener pendiente que el JWT está formado no encriptado.
                    }

                });

                post("/listar", ctx -> {

                    String user = ctx.formParam("usuario");
                    Usuario usuario = UsuarioServices.getInstancia().find(user);
                    EntityManager em =FormularioServices.getInstancia().getEntityManager();

                    String queryString = "SELECT u FROM Ubicacion u " +
                            "WHERE u.formulario.usuario.username = :username";

                    Query query = em.createQuery(queryString);

                    query.setParameter("username", usuario.getUsername());

                    List<Formulario> lista = query.getResultList();

                    ctx.json(lista);

                });

                before("/newForm",ctx -> {

                    System.out.println("Analizando que exista el token");

                    //informacion para consultar en la trama.
                    String header = "Authorization";
                    String prefijo = "Bearer";

                    //Verificando si existe el header de autorizacion.
                    String headerAutentificacion = ctx.header(header);
                    if(headerAutentificacion == null || !headerAutentificacion.startsWith(prefijo)){
                        System.out.println("No hay");
                        throw new ForbiddenResponse("No tiene permiso para acceder al recurso");
                    }else{

                        //recuperando el token y validando
                        String tramaJwt = headerAutentificacion.replace(prefijo, "");
                        try {
                            Claims claims = Jwts.parser()
                                    .setSigningKey(Keys.hmacShaKeyFor(LLAVE_SECRETA.getBytes()))
                                    .parseClaimsJws(tramaJwt).getBody();
                            System.out.println("Mostrando el JWT recibido: " + claims.toString());
                        }catch (ExpiredJwtException | MalformedJwtException | SignatureException e){ //Excepciones comunes
                            throw new ForbiddenResponse(e.getMessage());
                        }

                        //En este punto puedo realizar validaciones en función a los permisos del usuario.
                        //tener pendiente que el JWT está formado no encriptado.
                    }

                });

                post("/newForm", ctx -> {

                    Gson g = new Gson();
                    DataWS dws = g.fromJson(ctx.body(), DataWS.class);

                    String nombre = dws.getNombre()+" "+dws.getApellido();
                    String latitud = dws.getLatitud();
                    String longitud = dws.getLongitud();
                    String provincia = dws.getProvincia();
                    String nivelacad = dws.getNivelacad();
                    String usuario = dws.getUsuario();
                    String foto = dws.getFoto();

                    Foto FOTO = new Foto(nombre, foto.substring(5, 15), foto.substring(23));
                    FotoServices.getInstancia().crear(FOTO);

                    Usuario Usuario = UsuarioServices.getInstancia().find(usuario);
                    Formulario form = new Formulario(nombre,provincia,nivelacad,Usuario, FOTO);
                    FormularioServices.getInstancia().crear(form);

                    Ubicacion ubicacion = new Ubicacion(longitud,latitud,form);
                    UbicacionServices.getInstancia().crear(ubicacion);

                    ctx.result("Formulario creado con exito");

                });


                post("/login", ctx -> {

                    String user = ctx.formParam("usuario");
                    String pass = ctx.formParam("pass");

                    if(UsuarioServices.getInstancia().verifyUser(pass,user) == null){
                        //TODO: BAD LOGIN VIA REST API

                    }else{
                        LoginResponse lr = generacionJsonWebToken(user);
                        ctx.json(lr);
                    }


                });

            });

        });*/

    }

    /**
     * JWT GENERATION
     * @param usuario
     * @return
     */

    private static LoginResponse generacionJsonWebToken(String usuario){
        LoginResponse loginResponse = new LoginResponse();
        //generando la llave.
        SecretKey secretKey = Keys.hmacShaKeyFor(LLAVE_SECRETA.getBytes());
        //Generando la fecha valida
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(24*60);
        System.out.println("La fecha actual: "+localDateTime.toString());

        // creando la trama.
        String jwt = Jwts.builder()
                .setIssuer("2P-PUCMM")
                .setSubject("2P ITA")
                .setExpiration(Date.from(localDateTime.toInstant(ZoneOffset.ofHours(-4))))
                .claim("usuario", usuario)
                .signWith(secretKey)
                .compact();
        loginResponse.setToken(jwt);
        return loginResponse;
    }

    /**
     *
     * @param server
     * @param contextString
     * @return
     * @throws Exception
     */

    private static HttpContext build(Server server, String contextString) throws Exception {
        JettyHttpServer jettyHttpServer = new JettyHttpServer(server, true);
        JettyHttpContext ctx = (JettyHttpContext) jettyHttpServer.createContext(contextString);
        Method method = JettyHttpContext.class.getDeclaredMethod("getJettyContextHandler");
        method.setAccessible(true);
        HttpSpiContextHandler contextHandler = (HttpSpiContextHandler) method.invoke(ctx);
        contextHandler.start();
        return ctx;
    }

}
