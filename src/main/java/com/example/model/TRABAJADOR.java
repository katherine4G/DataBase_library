package com.example.model;

public class TRABAJADOR{
    
    private int ID;
    private String nombre;
    private String apellido;
    private String cedula;
    private String correo;
    private String cargo;

    public TRABAJADOR(){}
    public TRABAJADOR(int ID,String name,String lastName, String worker_id,String email,String job_position){
        this.ID = ID;
        this.correo = email;
        this.cargo = job_position;
        this.apellido = lastName;
        this.nombre = name;
        this.cedula = worker_id;
    }
    
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }    

    public String getName() {
        return nombre;
    }
    public void setName(String name) {
        this.nombre = name;
    }
    
    public String getLastName() {
        return apellido;
    }
    public void setLastName(String Lname) {
        this.apellido = Lname;
    }
    public String getWorker_id() {
        return cedula;
    }
    public void setWorker_id(String worker_id) {
        this.cedula = worker_id;
    }
    public String getEmail() {
        return correo;
    }
    public void setEmail(String email) {
        this.correo = email;
    }
    public String getJob_position() {
        return cargo;
    }
    public void setJob_position(String job_position) {
        this.cargo = job_position;
    }
}