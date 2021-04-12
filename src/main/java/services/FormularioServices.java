package services;

import encapsulaciones.Formulario;

public class FormularioServices extends DBManage<Formulario> {

    private static FormularioServices instancia;

    private FormularioServices() { super(Formulario.class); }

    public static FormularioServices getInstancia(){
        if(instancia==null){
            instancia = new FormularioServices();
        }

        return instancia;
    }

    /**
     * METHODS FOR THIS CLASS
     */



}
