package edu.pucmm.ia.ds.funciones;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.pucmm.ia.ds.encapsulaciones.HolaMundoResponde;

/**
 * Creando la clase contexto para recibir la información
 * utilizando la interfaz.
 * Para la alta en AWS incluir la siguiente clase: edu.pucmm.ia.ds.funciones.FuncionBasica::handleRequest
 */
public class FuncionBasica implements RequestHandler<String, HolaMundoResponde> {

    /**
     * La entrada debe ser String valido como JSON, de lo contrario muestra un error.
     * Notar que puedo pasar variables de ambientes en la creación de la función.
     * @param input
     * @param context
     * @return
     */
    @Override
    public HolaMundoResponde handleRequest(String input, Context context) {
        context.getLogger().log(String.format("Ejecutando la función id: %s, ",context.getAwsRequestId() ));
        String variableAmbiente = System.getenv("VARIABLE_AMBIENTE");
        context.getLogger().log(String.format("La variable de ambiente = %s, ",variableAmbiente));
        return new HolaMundoResponde("Hola Mundo "+input); //Convierte a JSON
    }
}
