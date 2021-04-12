package services;

import encapsulaciones.Usuario;

public class UsuarioServices extends DBManage<Usuario> {

    private static UsuarioServices instancia;

    private UsuarioServices() { super(Usuario.class); }

    public static UsuarioServices getInstancia(){
        if(instancia==null){
            instancia = new UsuarioServices();
        }

        return instancia;
    }

    /**\
     * METHODS FOR THIS CLASS
     */

    public Usuario verifyUser(String password, String user){

        Usuario aux_user = find(user);
        if(aux_user == null){
            return null;
        }else if(aux_user.getPassword().equals(password)){
            return aux_user;
        }else{
            return null;
        }

    }
}
