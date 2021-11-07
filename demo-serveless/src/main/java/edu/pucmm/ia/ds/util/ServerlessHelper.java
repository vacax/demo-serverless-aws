package edu.pucmm.ia.ds.util;

public class ServerlessHelper {

    public enum VariableAmbiente{

        TABLA("TABLA_ESTUDIANTE");

        private String valor;

        VariableAmbiente(String valor) {
            this.valor = valor;
        }

        public String getValor() {
            return valor;
        }
    }

    public static String getNombreTabla(){
        return System.getenv(VariableAmbiente.TABLA.getValor());
    }
}
