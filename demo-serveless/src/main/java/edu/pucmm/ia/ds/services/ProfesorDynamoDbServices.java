package edu.pucmm.ia.ds.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import edu.pucmm.ia.ds.encapsulaciones.Estudiante;
import edu.pucmm.ia.ds.encapsulaciones.Profesor;
import edu.pucmm.ia.ds.util.ServerlessHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clase para encapsular la funcionalidad CRUD en la base de datos DynamoDB
 * utilizando el DynamoDBMapper, ver:
 * https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBMapper.Methods.html
 */
public class ProfesorDynamoDbServices {

    /**
     * Utilizando el Dynamo Mapper que funciona como un ORM.
     * @param profesor
     * @param context
     * @return
     */
    public ProfesorResponse insertarProfesor(Profesor profesor, Context context){

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        if(profesor.getCedula().isEmpty() || profesor.getNombre().isEmpty()){
           throw new RuntimeException("Datos enviados no son validos");
        }

        try {

           mapper.save(profesor);

        }catch (Exception e){
           return new ProfesorResponse(true, e.getMessage(), null);
        }

        //Retornando
        return new ProfesorResponse(false, null, profesor);
    }

    /**
     * Metodo para retornar el listado de todos los elementos de la tablas
     * @param filtro
     * @param context
     * @return
     */
    public ListarProfesorResponse listarProfesores(FiltroListaProfesor filtro, Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        List<Profesor> profesores = mapper.scan(Profesor.class, new DynamoDBScanExpression());

        return new ListarProfesorResponse(false, "", profesores);
    }

    /**
     * Función para eliminar un estudiantes
     * @param profesor
     * @param context
     * @return
     */
    public ProfesorResponse eliminarProfesores(Profesor profesor, Context context){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        mapper.delete(profesor);

        return new ProfesorResponse(false, null, profesor);
    }

    /**
     * Representa el objeto que encapsula la información de la consulta
     */
    public static class ListarProfesorResponse {
        boolean error;
        String mensajeError;
        List<Profesor> profesores;

        public ListarProfesorResponse() {
        }

        public ListarProfesorResponse(boolean error, String mensajeError, List<Profesor> profesores) {
            this.error = error;
            this.mensajeError = mensajeError;
            this.profesores = profesores;
        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public String getMensajeError() {
            return mensajeError;
        }

        public void setMensajeError(String mensajeError) {
            this.mensajeError = mensajeError;
        }

        public List<Profesor> getProfesores() {
            return profesores;
        }

        public void setProfesores(List<Profesor> profesores) {
            this.profesores = profesores;
        }
    }

    /**
     *  Encapsulación del objeto de respuesta.
     */
    public static class ProfesorResponse {
        boolean error;
        String mensajeError;
        Profesor profesor;

        public ProfesorResponse(){

        }

        public ProfesorResponse(boolean error, String mensajeError, Profesor profesor) {
            this.error = error;
            this.mensajeError = mensajeError;
            this.profesor = profesor;
        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public String getMensajeError() {
            return mensajeError;
        }

        public void setMensajeError(String mensajeError) {
            this.mensajeError = mensajeError;
        }

        public Profesor getProfesor() {
            return profesor;
        }

        public void setProfesor(Profesor profesor) {
            this.profesor = profesor;
        }
    }

    public static class FiltroListaProfesor {
        String filtro;

        public String getFiltro() {
            return filtro;
        }

        public void setFiltro(String filtro) {
            this.filtro = filtro;
        }
    }

}
