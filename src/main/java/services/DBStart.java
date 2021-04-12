package services;

import org.h2.tools.Server;

import java.sql.SQLException;

public class DBStart {

    private static DBStart instancia;

    private DBStart(){

    }

    public static DBStart getInstancia(){
        if(instancia == null){
            instancia = new DBStart();
        }
        return instancia;
    }

    public void startDb() {
        try {

            //SERVER MODE
            Server.createTcpServer("-tcpPort",
                    "9092",
                    "-tcpAllowOthers",
                    "-tcpDaemon").start();
            //WEB CLIENT
            String status = Server.createWebServer("-trace", "-webPort", "0").start().getStatus();
            System.out.println("WEB CLIENT STATUS: "+ status);

        }catch (SQLException ex){
            System.out.println("Problema con la base de datos: "+ex.getMessage());
        }
    }

    public void init(){
        startDb();
    }

}
