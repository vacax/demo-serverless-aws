package edu.pucmm.ia.ds.funciones;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.google.gson.Gson;
import edu.pucmm.ia.ds.encapsulaciones.Profesor;
import edu.pucmm.ia.ds.services.EstudianteDynamoDbServices;
import edu.pucmm.ia.ds.services.ProfesorDynamoDbServices;

import java.util.HashMap;

/**
 * Función para trabajar con el CRUD de profesores en AWS.
 */
public class FuncionCRUDProfesor implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final ProfesorDynamoDbServices profesorDynamoDbServices = new ProfesorDynamoDbServices();
    private final Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        Profesor profesor = null;
        //Recuperando la entrada, ver en CloudWatch la salida:
        context.getLogger().log("Petición: "+gson.toJson(request));

        //
        String metodoHttp = request.getHttpMethod();

        context.getLogger().log("Metodo de acceso: "+metodoHttp);
        context.getLogger().log("Parametros enviados: "+request.getPathParameters());
        context.getLogger().log("Cuerpo enviado: "+request.getBody());

        //
        //Realizando la operacion
        String salida = "";
        switch (metodoHttp){
            case "GET":
                ProfesorDynamoDbServices.ListarProfesorResponse listarProfesorResponse = profesorDynamoDbServices.listarProfesores(null, context);
                salida = gson.toJson(listarProfesorResponse);
                break;
            case "POST":
            case "PUT":
                profesor = gson.fromJson(request.getBody(), Profesor.class);
                profesorDynamoDbServices.insertarProfesor(profesor, context);
                salida = gson.toJson(profesor);
                break;
            case "DELETE":
                profesor = gson.fromJson(request.getBody(), Profesor.class);
                profesorDynamoDbServices.eliminarProfesores(profesor, context);
                salida = gson.toJson(profesor);
                break;
        }

        //Headers
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        //Salida.
        APIGatewayProxyResponseEvent reponse = new APIGatewayProxyResponseEvent();
        reponse.setStatusCode(200);
        reponse.setHeaders(headers);
        reponse.setBody(salida);

        //
        return reponse;

    }
}
