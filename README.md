<br />
<div align="center">
<h3 align="center">NELUMBO</h3>
</div>

# NelumboPrueba
El presente proyecto es reallizado para llevar a cabo la gestion de un parqueadero.

## Inicio

### Requisitos

* JDK 17 o superior [https://jdk.java.net/java-se-ri/17](https://jdk.java.net/java-se-ri/17)
* Maven [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
* PostgreSQL [https://www.postgresql.org/download/](https://www.postgresql.org/download/)

### Herramientas Recomendadas
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### Instalacion
1. Clonar el Repositorio : git clone https://github.com/NAJILUC/NelumboPrueba.git
2. Crear una base de datos en PostgreSQL con el nombre 'parqueadero'
3. Configurar las configuraciones de conexion. 
```
# src/main/resources/application-dev.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/parqueadero
spring.datasource.username=${USERNAME}
spring.datasource.password=${PASSWORD}
```

## Uso
1. Abrir cada carpeta ('mensaje', 'parqueadero') como proyecto independiente.
2. Ejecutar ambos proyectos, es importante tener 'mensaje' para que algunas funcionalidades de 'parqueadero' no fallen.
3. Para probar las diferentes funcionalidades importando la collection 'Parqueadero.postman_collection' en postman, ahi se encuentran especificadas.