# FinkUp REST API

This project involves the development of a RESTful API used in the FinkUp application to store data on a server using Spring Boot and MySQL.

It is attached to the https://github.com/mohdiop/finkup project.

## API Documentation
**Note:** Operations are performed on the Fink object, the schema of which is given in the following Kotlin file:
  ```kotlin
    @Table(name = "fink")
    data class Fink(
        @Id
        var finkId: Long,
        var finkTitle: String,
        var finkContent: String,
        var finkDate: Long = System.currentTimeMillis()
    )
  ```
  Example of the `JSON` body:
  ```json
    {
      "finkId": 1,
      "finkTitle": "Un titre",
      "finkContent": "Un contenu",
      "finkDate": 1702470144456
    }
  ```
  The `finkDate` field is represented in milliseconds.

**Base URL** : `http://localhost:port_number`.

The `port_number` is `6874` by default you can change it as you want to an available port in the `application.properties` file **src/main/resources/application.properties**.

You can also change the properties to specify your MySQL database connexion config:
```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/finkup
spring.datasource.username=root
spring.datasource.password=

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

server.port=6874
```

### Connect to the Server

- **Type :** GET
- **Endpoint :** `/`
- **Description :** Checks if the connection to the server is successful.
- **Response :** "Connect to server successfully!"

### Add a Fink

- **Type :** POST
- **Endpoint :** `/addFink`
- **Description :** Adds a new Fink object to the database.
- **Request Body :** JSON object representing a Fink.
- **Response :** "Fink added successfully!".

### Add Finks in Bulk

- **Type :** POST
- **Endpoint :** `/addFinks`
- **Description :** Adds a list of Fink objects to the database.
- **Request Body :** List of JSON objects representing Finks.
- **Response :** "All finks added successfully!".

### Get All Finks

- **Type :** GET
- **Endpoint :** `/finks`
- **Description :** Retrieves all Fink objects from the database.
- **Response :** List of Fink objects.

### Get a Fink by id

- **Type :** GET
- **Endpoint :** `/finkId/{id}`
- **Description :** Retrieves a Fink object based on its id.
- **Path Parameter :** `id` - ID of the Fink object.
- **Response :** Fink object corresponding to the specified id.

### Get a Fink by Title

- **Type :** GET
- **Endpoint :** `/finkTitle/{title}`
- **Description :** Retrieves a Fink object based on its title.
- **Path Parameter :** `title` - Title of the Fink object.
- **Response :** Fink object corresponding to the specified title.

### Get a Fink by Content

- **Type :** GET
- **Endpoint :** `/finkContent/{content}`
- **Description :** Retrieves a Fink object based on its content.
- **Path Parameter :** `content` - Content of the Fink object.
- **Response :** Fink object corresponding to the specified content.

### Update a Fink

- **Type :** PUT
- **Endpoint :** `/update`
- **Description :** Updates an existing Fink object in the database.
- **Request Body :** JSON object representing an updated Fink.
- **Response :**
  - "Fink updated successfully!" if the Fink exists; otherwise,
  - "This fink is not added yet so it's been added!" and adds the Fink.

### Delete a Fink by id

- **Type :** DELETE
- **Endpoint :** `/delete/{id}`
- **Description :** Deletes a Fink object from the database based on its id.
- **Path Parameter :** `id` - id of the Fink object.
- **Response :** "Fink deleted successfully".

### Delete All Finks

- **Type :** DELETE
- **Endpoint :** `/delete`
- **Description :** Deletes all Fink objects from the database.
- **Response :** "All finks deleted successfully!".

Make sure to config your MySQL Server, create a database named **finkup** and start it before run the project.
