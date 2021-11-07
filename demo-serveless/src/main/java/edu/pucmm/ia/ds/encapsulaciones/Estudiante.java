package edu.pucmm.ia.ds.encapsulaciones;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Clase que utiliza en enlace para DynamoDB.
 */
@DynamoDBTable(tableName="estudiantes")
public class Estudiante {

    @DynamoDBHashKey(attributeName="matricula")
    private int matricula;
    @DynamoDBAttribute(attributeName = "nombre")
    private String nombre;
    @DynamoDBAttribute(attributeName = "correo")
    private String correo;
    @DynamoDBAttribute(attributeName = "carrera")
    private String carrera;

    public Estudiante(){
        
    }

    public Estudiante(int matricula, String nombre) {
        this.matricula = matricula;
        this.nombre = nombre;
    }

    public Estudiante(int matricula, String nombre, String correo, String carrera) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.correo = correo;
        this.carrera = carrera;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "matricula=" + matricula +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", carrera='" + carrera + '\'' +
                '}';
    }
}
