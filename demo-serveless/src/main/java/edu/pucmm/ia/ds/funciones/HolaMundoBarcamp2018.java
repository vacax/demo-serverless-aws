package edu.pucmm.ia.ds.funciones;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class HolaMundoBarcamp2018 {

    /**
     * 
     * @param entrada
     * @param context
     * @return
     */
    public HolaMundoBarcampResponse holaMundo(HolaMundoRequest entrada, Context context){
        LambdaLogger logger = context.getLogger();
        logger.log("Hola Mundo prueba de Serverless....");
        logger.log("El ID del proceso: "+context.getAwsRequestId());

        return new HolaMundoBarcampResponse("Hola Mundo "+entrada.mensaje+" :-D");
    }

    static class HolaMundoRequest {

        String mensaje;

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }

    static class HolaMundoBarcampResponse {

        String mensaje;

        public HolaMundoBarcampResponse() {
        }

        public HolaMundoBarcampResponse(String mensaje) {
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
