package edu.pucmm.ia.ds.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import edu.pucmm.ia.ds.encapsulaciones.Estudiante;
import edu.pucmm.ia.ds.util.ServerlessHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase para encapsular la funcionalidad CRUD en la base de datos DynamoDB
 */
public class EstudianteDynamoDbServices {

    /**
     * Función simplificando la salida relacionado
     * @param estudiante
     * @param context
     * @return
     */
    public EstudianteResponse insertarEstudianteTabla(Estudiante estudiante, Context context){

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDB dynamoDB = new DynamoDB(client);

        if(estudiante.getMatricula() == 0 || estudiante.getNombre().isEmpty()){
           throw new RuntimeException("Datos enviados no son validos");
        }

        try {

            Table table = dynamoDB.getTable(ServerlessHelper.getNombreTabla());
            Item item = new Item().withPrimaryKey("matricula", estudiante.getMatricula())
                    .withString("nombre", estudiante.getNombre())
                    .withString("carrera", estudiante.getCarrera());

            PutItemOutcome putItemOutcome = table.putItem(item);

        }catch (Exception e){
           return new EstudianteResponse(true, e.getMessage(), null);
        }

        //Retornando
        return new EstudianteResponse(false, null, estudiante);
    }

    /**
     * Metodo para retornar el listado de todos los elementos de la tablas
     * @param filtro
     * @param context
     * @return
     */
    public ListarEstudiantesResponse listarEstudiantes(FiltroListaEstudiante filtro, Context context) {
        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

        List<Estudiante> estudiantes = new ArrayList<>();

        ScanRequest scanRequest = new ScanRequest().withTableName(ServerlessHelper.getNombreTabla());
        ScanResult result = null;

        do {// La consulta vía ScanRequest solo retorna 1 MB de datos por iteracion,
            //debemos iterar.

            if (result != null) {
                scanRequest.setExclusiveStartKey(result.getLastEvaluatedKey());
            }
            result = ddb.scan(scanRequest);
            List<Map<String, AttributeValue>> rows = result.getItems();

            // Iterando todos los elementos
            for (Map<String, AttributeValue> mapEstudiantes : rows) {
                System.out.println(""+mapEstudiantes);
                //
                AttributeValue matriculaAtributo = mapEstudiantes.get("matricula");
                AttributeValue nombreAtributo = mapEstudiantes.get("nombre");
                AttributeValue carreraAtributo = mapEstudiantes.get("carrera");
                //
                Estudiante tmp = new Estudiante();
                tmp.setMatricula(Integer.valueOf(matriculaAtributo.getN()));
                if(nombreAtributo!=null){
                   tmp.setNombre(nombreAtributo.getS());
                }
                if(carreraAtributo!=null){
                    tmp.setCarrera(carreraAtributo.getS());
                }
                //
                estudiantes.add(tmp);
            }

        } while (result.getLastEvaluatedKey() != null);

        return new ListarEstudiantesResponse(false, "", estudiantes);
    }

    /**
     * Función para eliminar un estudiantes
     * @param estudiante
     * @param context
     * @return
     */
    public EstudianteResponse eliminarEstudiante(Estudiante estudiante, Context context){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable(ServerlessHelper.getNombreTabla());

        DeleteItemOutcome outcome = table.deleteItem("matricula", estudiante.getMatricula());
        return new EstudianteResponse(false, null, estudiante);
    }

    /**
     * Representa el objeto que encapsula la información de la consulta
     */
    public static class ListarEstudiantesResponse{
        boolean error;
        String mensajeError;
        List<Estudiante> estudiantes;

        public ListarEstudiantesResponse() {
        }

        public ListarEstudiantesResponse(boolean error, String mensajeError, List<Estudiante> estudiantes) {
            this.error = error;
            this.mensajeError = mensajeError;
            this.estudiantes = estudiantes;
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

        public List<Estudiante> getEstudiantes() {
            return estudiantes;
        }

        public void setEstudiantes(List<Estudiante> estudiantes) {
            this.estudiantes = estudiantes;
        }
    }

    /**
     *  Encapsulación del objeto de respuesta.
     */
    public static class EstudianteResponse{
        boolean error;
        String mensajeError;
        Estudiante estudiante;

        public EstudianteResponse(){

        }

        public EstudianteResponse(boolean error, String mensajeError, Estudiante estudiante) {
            this.error = error;
            this.mensajeError = mensajeError;
            this.estudiante = estudiante;
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

        public Estudiante getEstudiante() {
            return estudiante;
        }

        public void setEstudiante(Estudiante estudiante) {
            this.estudiante = estudiante;
        }
    }

    public static class FiltroListaEstudiante{
        String filtro;

        public String getFiltro() {
            return filtro;
        }

        public void setFiltro(String filtro) {
            this.filtro = filtro;
        }
    }

}
