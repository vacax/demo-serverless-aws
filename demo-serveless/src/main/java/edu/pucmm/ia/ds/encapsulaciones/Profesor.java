package edu.pucmm.ia.ds.encapsulaciones;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="profesores")
public class Profesor {

    @DynamoDBHashKey(attributeName="cedula")
    private String cedula;
    @DynamoDBAttribute(attributeName = "nombre")
    private String nombre;

    public Profesor() {
    }

    public Profesor(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
