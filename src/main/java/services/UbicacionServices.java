package services;

import encapsulaciones.Ubicacion;

public class UbicacionServices extends DBManage<Ubicacion> {

    private static UbicacionServices instancia;

    private UbicacionServices() { super(Ubicacion.class); }

    public static UbicacionServices getInstancia(){
        if(instancia==null){
            instancia = new UbicacionServices();
        }

        return instancia;
    }

    /**
     * METHODS FOR THIS CLASS
     */



}
