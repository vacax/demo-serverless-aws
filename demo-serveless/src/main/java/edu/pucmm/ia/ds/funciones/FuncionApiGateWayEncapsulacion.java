package edu.pucmm.ia.ds.funciones;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Utilizando la Interfaz propia de la librería para aws-lambda-java-events, ver en
 * https://github.com/aws/aws-lambda-java-libs
 */
public class FuncionApiGateWayEncapsulacion {

    /**
     * Las clases utilizadas corresponde a la implementación en la librería del SDK.
     * Nos ayudan a trabajar de una manera más rapida. Trabaja para la versión Api gateway HTTP
     * @param request
     * Encapsulación para la versión API Gateway HTTP.
     * @param context
     * @return
     * Encapsulación para la versión API Gateway HTTP.
     * @throws IOException
     */
    public APIGatewayV2HTTPResponse manejadorApiHttp(APIGatewayV2HTTPEvent request, Context context)
            throws IOException {
        //Recuperando la entrada, ver en CloudWatch la salida:
        context.getLogger().log("Metodo de acceso: "+request.getRequestContext().getHttp().getMethod());
        context.getLogger().log("Parametros enviados: "+request.getRawQueryString());
        context.getLogger().log("Cuerpo enviado: "+request.getBody());
        //Salida.
        APIGatewayV2HTTPResponse reponse = new APIGatewayV2HTTPResponse();
        reponse.setStatusCode(200);
        reponse.setBody("Hola Mundo Serverless Utilizando APIGateway tipo HTTP vía  :-D");
        return reponse;
    }


    /**
     * Las clases utilizadas corresponde a la implementación en la librería del SDK.
     * Nos ayudan a trabajar de una manera más rapida. Trabaja para la versión Api gateway Rest
     * @param request
     * @param context
     * @return
     * @throws IOException
     */
    public APIGatewayProxyResponseEvent manejadorApiRest(APIGatewayProxyRequestEvent request, Context context)
            throws IOException {
        //Recuperando la entrada, ver en CloudWatch la salida:
        context.getLogger().log("Metodo de acceso: "+request.getHttpMethod());
        context.getLogger().log("Cuerpo enviado: "+request.getBody());
        //Salida.
        APIGatewayProxyResponseEvent reponse = new APIGatewayProxyResponseEvent();
        reponse.setStatusCode(200);
        reponse.setBody("Hola Mundo Serverless Utilizando APIGateway tipo REST vía  :-D");
        return reponse;
    }


}
