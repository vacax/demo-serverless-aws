package edu.pucmm.ia.ds.funciones;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import edu.pucmm.ia.ds.encapsulaciones.Estudiante;
import edu.pucmm.ia.ds.services.EstudianteDynamoDbServices;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

/**
 * Funcion para trabajar el CRUD de la entidad Estudiantes.
 * Es importante crear los permisos necesarios para conectar DynamoDB con el servicio de los Serverless.
 * Se cambia el rol del serverless, pueden utilizar desde la plantilla "Permisos de microservicios sencillos" o
 * "Simple microservice permissions"
 */
public class FuncionCRUDEstudiante  implements RequestStreamHandler {

    //Instanciando objeto el manejo de la base de datos.
    private EstudianteDynamoDbServices funcionesDynamoDbEstudiante = new EstudianteDynamoDbServices();
    private Gson gson = new Gson();

    /**
     * Estaremos analizando el metodo de acceso a lo interno de la función y realizando la conversación.
     * @param input
     * @param output
     * @param context
     * @throws IOException
     */
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        //Objetos para el control de la salida.
        JSONParser parser = new JSONParser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String cuerpoRecibido = null;
        JSONObject responseJson = new JSONObject();
        String salida = "";
        Estudiante estudiante = null;
        //
        try {
            //Parseando el objeto.
            JSONObject evento = (JSONObject) parser.parse(reader);

            //Ver la salida por la consola sobre la trama enviada por el APIGateway
            context.getLogger().log(""+evento.toJSONString());

            //Recuperando el metodo de acceso de la llamada del API.
            if(evento.get("requestContext")==null){
                throw new IllegalArgumentException("No respesta el API de entrada");
            }
            //String metodoHttp = ((JSONObject)((JSONObject)evento.get("httpMethod")).get("http")).get("method").toString();
            String metodoHttp = evento.get("httpMethod").toString();

            //Realizando la operacion
            switch (metodoHttp){
                case "GET":
                    EstudianteDynamoDbServices.ListarEstudiantesResponse listarEstudiantesResponse = funcionesDynamoDbEstudiante.listarEstudiantes(null, context);
                    salida = gson.toJson(listarEstudiantesResponse);
                    break;
                case "POST":
                case "PUT":
                    estudiante = getEstudianteBodyJson(evento);
                    funcionesDynamoDbEstudiante.insertarEstudianteTabla(estudiante, context);
                    salida = gson.toJson(estudiante);
                    break;
                case "DELETE":
                    estudiante = getEstudianteBodyJson(evento);
                    funcionesDynamoDbEstudiante.eliminarEstudiante(estudiante, context);
                    salida = gson.toJson(estudiante);
                    break;
            }

            //La información enviada por el metodo Post o Put estará disponible en la propiedad body:
            if(evento.get("body")!=null){
                cuerpoRecibido = evento.get("body").toString();
            }

            //Respuesta en el formato esperado:
            JSONObject responseBody = new JSONObject();
            responseBody.put("data", JsonParser.parseString(salida));
            responseBody.put("entrada", cuerpoRecibido);

            JSONObject headerJson = new JSONObject();
            headerJson.put("mi-header", "Mi propio header");
            headerJson.put("Content-Type", "application/json");

            responseJson.put("statusCode", 200);
            responseJson.put("headers", headerJson);
            responseJson.put("body", responseBody.toString());

        }catch (Exception ex){
            responseJson.put("statusCode", 400);
            responseJson.put("exception", ex);
        }

        //Salida
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toString());
        writer.close();
    }

    /**
     *
     * @param json
     * @return
     */
    private Estudiante getEstudianteBodyJson(JSONObject json) throws IllegalArgumentException{
        if(json.get("body")==null){
            throw new IllegalArgumentException("No envio el cuerpo en la trama.");
        }
        Estudiante estudiante =gson.fromJson(json.get("body").toString(), Estudiante.class);
        return estudiante;
    }
}
