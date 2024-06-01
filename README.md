# Primer Microservicio con Swagger Codegen v3

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

## Descripción

Este proyecto es un microservicio construido con Spring Boot y Swagger Codegen v3. Está diseñado para gestionar empleados y centros, proporcionando endpoints para la creación, modificación, eliminación y consulta de empleados.

## Características

- **Creación de Empleados**: Permite la creación de nuevos empleados.
- **Modificación de Empleados**: Permite la modificación de datos de empleados existentes.
- **Eliminación de Empleados**: Permite la eliminación de empleados por ID.
- **Consulta de Empleados**: Permite la consulta de empleados por ID y la lista de todos los empleados.

## Dependencias

El proyecto utiliza las siguientes dependencias:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>2.0.1.Final</version>
</dependency>
<dependency>
    <groupId>org.openapitools</groupId>
    <artifactId>jackson-databind-nullable</artifactId>
    <version>0.2.2</version>
</dependency>
<dependency>
    <groupId>io.swagger.core.v3</groupId>
    <artifactId>swagger-annotations</artifactId>
    <version>2.2.21</version>
</dependency>
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
<dependency>
    <groupId>javax.annotation</groupId>
    <artifactId>javax.annotation-api</artifactId>
    <version>1.3.2</version>
</dependency>
<dependency>
    <groupId>com.github.joschi.jackson</groupId>
    <artifactId>jackson-datatype-threetenbp</artifactId>
    <version>2.4.1</version>
</dependency>
<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-core</artifactId>
    <version>1.5.16</version>
</dependency>
<dependency>
    <groupId>com.github.joschi.jackson</groupId>
    <artifactId>jackson-datatype-threetenbp</artifactId>
    <version>2.15.2</version>
</dependency>
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.2.Final</version>
</dependency>
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>1.5.2.Final</version>
    <scope>provided</scope>
</dependency>
```
## Configuración de la base de datos
El proyecto utiliza una base de datos en memoria H2 configurada a través del archivo application.yml:

```yaml
server:
  port: 9090
spring:
  application:
    name: app
  datasource:
    driverClassName: org.h2.Driver
    password: sa
    url: jdbc:h2:mem:testdb
    username: sa
  h2:
    console:
      enabled: true
      path: /h2
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    defer-datasource-initialization: true
    hibernate:
      ddl-auto: update
    show-sql: true
```
## Esquema de la base de datos
El esquema de la base de datos se define en el archivo schema.sql:
```sql
DROP TABLE IF EXISTS EMPLEADOS;
DROP TABLE IF EXISTS CENTROS;

CREATE TABLE CENTROS (
    NUM_CENTRO INT PRIMARY KEY,
    NOMBRE_CENTRO VARCHAR(100)
);

CREATE TABLE EMPLEADOS (
    ID_EMPLEADO INT PRIMARY KEY,
    NOMBRE VARCHAR(100),
    ID_CENTRO INT,
    FOREIGN KEY (ID_CENTRO) REFERENCES CENTROS(NUM_CENTRO)
);
```
## Datos Iniciales
Los datos iniciales se cargan a través del archivo data.sql:
```sql
-- Insertar datos en la tabla CENTROS
INSERT INTO CENTROS (NUM_CENTRO, NOMBRE_CENTRO)
VALUES
(101, 'ViewNext'),
(102, 'Indra'),
(103, 'Microsoft'),
(104, 'Accenture'),
(105, 'Capgemini'),
(106, 'Tata Consultancy Services'),
(107, 'Wipro'),
(108, 'Infosys'),
(109, 'Cognizant'),
(110, 'IBM');

-- Insertar datos en la tabla EMPLEADOS
INSERT INTO EMPLEADOS (ID_EMPLEADO, NOMBRE, ID_CENTRO)
VALUES
(1, 'Juan', 101),
(2, 'Ahmed Mohammed', 106),
(3, 'Yuki Tanaka', 104),
(4, 'Juan García', 103),
(5, 'María López', 108),
(6, 'Mohammed Ahmed', 104),
(7, 'David Rodríguez', 102),
(8, 'Laura Martínez', 101),
(9, 'Fatima Ahmed', 108),
(10, 'Takashi Suzuki', 106),
(11, 'Sara García', 106),
(12, 'Hiroshi Tanaka', 103),
(13, 'Rosa Pérez', 108),
(14, 'Yong Chen', 105);
```
## Documentación de la API
La API está documentada usando OpenAPI 3.0.0.
```yaml
openapi: 3.0.0
info:
  description: Contract associated with the EmpleadosYCentros application
  version: 1.0.0
  title: Empleados
  termsOfService: http://swagger.io/terms/
  contact:
    email: apiteam@swagger.io
  license:
    name: Apache 2.0
    url: http://www.apache.org/licenses/LICENSE-2.0.html
tags:
  - name: Empleados
    description: Controller for the Empleados application
paths:
  "/empleados/{id}":
    get:
      summary: Consulta un empleado por ID
      description: Consulta un empleado específico por su ID.
      operationId: getEmpleadoId
      tags:
        - Empleados
      parameters:
        - in: path
          name: id
          required: true
          schema:
            type: string
          description: Identificador del empleado
      responses:
        "200":
          description: OK
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/Empleado"
        "404":
          description: Empleado no encontrado
    put:
      summary: Modificación de empleado
      description: Modifica un empleado en la base de datos.
      operationId: putEmpleadoId
      tags:
        - Empleados
      parameters:
        - in: path
          name: id
          required: true
          schema:
            type: string
          description: Identificador del empleado
      requestBody:
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/Empleado"
      responses:
        "200":
          description: Empleado modificado satisfactoriamente.
        "400":
          description: Error en la solicitud o datos incorrectos.
        "404":
          description: Empleado no encontrado
        "500":
          description: Error interno del servidor
    delete:
      summary: Eliminación de empleado
      description: Elimina un empleado de la base de datos por su ID.
      operationId: deleteEmpleadoId
      tags:
        - Empleados
      parameters:
        - in: path
          name: id
          required: true
          schema:
            type: string
          description: Identificador del empleado
      responses:
        "204":
          description: Empleado eliminado satisfactoriamente.
        "404":
          description: Empleado no encontrado
        "500":
          description: Error interno del servidor
  "/empleados":
    get:
      summary: Consulta lista de empleados
      description: Consulta de los empleados dados de alta en la base de datos
      operationId: getEmpleados
      tags:
        - Empleados
      responses:
        "200":
          description: OK
          content:
            application/json:
              schema:
                type: object
                title: EmpleadosResponse
                properties:
                  Empleados:
                    type: array
                    description: Lista de empleados
                    items:
                      $ref: "#/components/schemas/Empleado"
        "500":
          description: Error interno del servidor
    post:
      summary: Creación de empleado
      description: Crea un nuevo empleado en la base de datos.
      operationId: postEmpleado
      tags:
        - Empleados
      requestBody:
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/Empleado"
      responses:
        "201":
          description: Empleado creado satisfactoriamente.
        "400":
          description: Error en la solicitud o datos incorrectos.
        "500":
          description: Error interno del servidor
  "/centros/{id}":
    get:
      summary: Consulta un centro por ID
      description: Consulta un centro específico por su ID.
      operationId: getCentroId
      tags:
        - Centros
      parameters:
        - in: path
          name: id
          required: true
          schema:
            type: string
          description: Identificador del centro
      responses:
        "200":
          description: OK
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/Centro"
        "404":
          description: Centro no encontrado
    put:
      summary: Modificación de centro
      description: Modifica un centro en la base de datos.
      operationId: putCentroId
      tags:
        - Centros
      parameters:
        - in: path
          name: id
          required: true
          schema:
            type: string
          description: Identificador del centro
      requestBody:
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/Centro"
      responses:
        "200":
          description: Centro modificado satisfactoriamente.
        "400":
          description: Error en la solicitud o datos incorrectos.
        "404":
          description: Centro no encontrado
        "500":
          description: Error interno del servidor
    delete:
      summary: Eliminación de centro
      description: Elimina un centro de la base de datos por su ID.
      operationId: deleteCentroId
      tags:
        - Centros
      parameters:
        - in: path
          name: id
          required: true
          schema:
            type: string
          description: Identificador del centro
      responses:
        "204":
          description: Centro eliminado satisfactoriamente.
        "404":
          description: Centro no encontrado
        "500":
          description: Error interno del servidor
  "/centros":
    get:
      summary: Consulta lista de centros
      description: Consulta de los centros dados de alta en la base de datos
      operationId: getCentros
      tags:
        - Centros
      responses:
        "200":
          description: OK
          content:
            application/json:
              schema:
                type: object
                title: CentrosResponse
                properties:
                  Centros:
                    type: array
                    description: Lista de centros
                    items:
                      $ref: "#/components/schemas/Centro"
        "500":
          description: Error interno del servidor
    post:
      summary: Creación de centro
      description: Crea un nuevo centro en la base de datos.
      operationId: postCentro
      tags:
        - Centros
      requestBody:
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/Centro"
      responses:
        "201":
          description: Centro creado satisfactoriamente.
        "400":
          description: Error en la solicitud o datos incorrectos.
        "500":
          description: Error interno del servidor
```
## Componentes
Empleado
```yaml
Empleado:
  type: object
  properties:
    id_empleado:
      type: integer
      description: El ID del empleado a asignar. Debe ser proporcionado.
    nombre:
      type: string
      description: El nombre del empleado.
    id_centro:
      type: integer
      description: El ID del centro al que está asociado el empleado.
    centro:
      type: string
      description: El nombre del centro al que está asociado el empleado.
```
Centro
```yaml
Centro:
  type: object
  properties:
    numCentro:
      type: integer
      description: El ID del centro.
    nombreCentro:
      type: string
      description: El nombre del centro.
```
