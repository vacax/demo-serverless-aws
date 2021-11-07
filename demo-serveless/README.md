# Proyecto Demo Serverless para AWS.

Proyecto con demostraciones del uso del servicio de Lambda de AWS para
implementar el concepto de Serverless. Contiene librerías Helper de Amazon e 
interpretación directa de las tramas recibidas.

Contiene ejemplos sobre:

* Llamada simple AWS Lambda ([FuncionHolaMundo](/src/main/java/edu/pucmm/ia/ds/funciones/FuncionHolaMundo.java)).
* LLamada mediante API GateWay ([FuncionApiGateWayEncapsulacion](/src/main/java/edu/pucmm/ia/ds/funciones/FuncionApiGateWayEncapsulacion.java)).
* Implementa CRUD basado en VueJS con DynamoDB ([FuncionCRUDEstudiante](/src/main/java/edu/pucmm/ia/ds/funciones/FuncionCRUDEstudiante.java)).

Para compilar y generar el jar que estaremos publicando:

`./gradlew shadowjar`

Ejemplo para asignar subir los ejemplos: