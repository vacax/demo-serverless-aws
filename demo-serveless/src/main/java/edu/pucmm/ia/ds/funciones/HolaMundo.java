package edu.pucmm.ia.ds.funciones;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.pucmm.ia.ds.encapsulaciones.HolaMundoResponde;

/**
 * Creando la clase contexto para recibir la información
 * utilizando la interfaz.
 */
public class HolaMundo implements RequestHandler<String, HolaMundoResponde> {

    /**
     * 
     * @param input
     * @param context
     * @return
     */
    @Override
    public HolaMundoResponde handleRequest(String input, Context context) {
        context.getLogger().log(String.format("Ejecutando la función id: %s, ",context.getAwsRequestId() ));
        return new HolaMundoResponde("Hola Mundo "+input);
    }
}
