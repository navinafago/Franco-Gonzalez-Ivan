package com.dragonregnan.sistemasdinamicos.model;

/**
 * Created by laura on 05/01/2016.
 */
public class usuariosModel {

    private int idUsuario;

    private String noBoleta;

    private String contrasena;

    private String nombre;

    private String apePaterno;

    private String apeMaterno;

    private String grupo;

    public int getIdUsuario(){
        return idUsuario;
    }

    public String getNoBoleta(){
        return noBoleta;
    }

    public String getContrasena(){
        return contrasena;
    }

    public String getNombre(){
        return nombre;
    }

    public String getApePaterno(){
        return apePaterno;
    }

    public String getApeMaterno(){
        return apeMaterno;
    }

    public String getGrupo(){
        return grupo;
    }

    public void setIdUsuario( int idUr){
        idUsuario = idUr;
    }

    public void setNoBoleta( String noBt){
        noBoleta = noBt;
    }

    public void setContrasena( String cn){
        contrasena = cn;
    }

    public void setNombre( String nr){
        nombre = nr;
    }

    public void setApePaterno( String apPn){
        apePaterno = apPn;
    }

    public void setApeMaterno( String apMn){
        apeMaterno = apMn;
    }

    public void setGrupo( String gp){
        grupo = gp;
    }

}
