package edu.pucmm.ia.ds.funciones;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class FuncionHolaMundo {

    /**
     * La entrada será pasada de JSON y convertida al objeto indicado.
     * @param entrada
     * @param context
     * @return
     * El retorno será la conversión a JSON del objeto que estamos trabajando.
     */
    public HolaMundoResponse holaMundo(HolaMundoRequest entrada, Context context){
        LambdaLogger logger = context.getLogger();
        logger.log("Hola Mundo prueba de Serverless....");
        logger.log("El ID del proceso: "+context.getAwsRequestId());

        return new HolaMundoResponse("Hola Mundo "+entrada.mensaje+" :-D");
    }

    /**
     * Clase para la conversión de la entrada
     */
    static class HolaMundoRequest {

        String mensaje;

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }

    /**
     * Clase para la encapsulación de la salida
     */
    static class HolaMundoResponse {

        String mensaje;

        public HolaMundoResponse() {
        }

        public HolaMundoResponse(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

    }
}
