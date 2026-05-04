# Introduction to GraphQL
=======================================
GraphQL is a *query Language for API's* and a **runtime for executing queries** with existing data. It was developed by Facebook in 2012 and released as an open-source project in 2015. GraphQL provides a more efficient, powerful, and flexible alternative to REST APIs.

## API's
=============================
API defines a contract between the client and the server.
- The strucuture of input and output data
- The operations that can be performed on the data
- The protocols and data formats that can be used for communication
- Data formats
    - JSON - Javascript Object Notation
    - XML
    - Text
    - HTML

The client application makes a HTTP request (GET/POST/PUT/DELETE) to the server, and the server responds with the requested data in the specified format. The server also sends the response status code (200, 201, 404, 403) to give some meaniningful information about the response.


### Different type of API Contracts
-----------------------------------------
1. SOAP (Simple Object Access Protocol)
2. REST (Representational State Transfer)
- Popular architectural style for designing networked applications
- Uses HTTP methods (GET, POST, PUT, DELETE) to perform operations on resources
- Resources are identified by URLs (Uniform Resource Locators)(/users, /products. /orders etc.)
- Change the representaion of the resources by calling the endpoints with different HTTP methods (GET /users, POST /users, PUT /users/1, DELETE /users/1)
- Make use of HTTP methods (GET, POST, PUT, DELETE) to perform operations on resources
- Along with the payload, we send the Headers (Content-Type, Authorization, Accept) to provide additional information about the request and response
- In the response, we get the status code (200, 201, 404, 403) to give some meaningful information about the response along with the data in the specified format (JSON, XML, Text, HTML)
3. GraphQL (Graph Query Language)


### Limitations of REST API's
-----------------------------------------
REST API's have been the standard for buidling web applications for many years. However, there are some limitations
- Treating different resources as separate endpoints the can lead to over-fetching or under fetching of the data
- Separte endpoints for different resources can lead to increased complexity and maintenance overhead
- REST API's often require multiple round trips to the server to fetch related data, which can lead to performance issues
- Can be less efficitne in terms of bendwidth usage, as they may return more data than necessary for a particular request.
  example response for a user endpoint in REST API
```json
{
    id: 1,
    name: "John Doe",
    email: "john_doe@gmail.com",
    address: {
        street: "123 Main St",
        city: "New York",
        state: "NY",
        zip: "10001"
    },
    orders: [
        {
            id: 1,
            product: "Laptop",
            price: 1000
        },
        {
            id: 2,
            product: "Phone",
            price: 500
        }
    ],
    payment: {
        method: "Credit Card",
        cardNumber: "**** **** **** 1234",
        expirationDate: "12/24"
    }
}
```
- No standard way to handle versioning, which can lead to compatibility issues when making changes to the API
```
/products - GET - returns a list of products
/v1/products - GET - returns a list of products (version 1)
/v2/products - GET - returns a list of products (version 2)
```
- No standard way to handle real-time updates or subscriptions
- Can be less flexible in terms of API evolution, as the changes to the API might require changes to the client code.
- In a nutshell, the server is doing all the heavy lifting and is smart, but the client is dumb and just consuming the data.

GraphQL was developed to address these limitations and provide a more efficient and flexible way to build APIs where the client is in the driver's seat and can specify exactly what data it needs, and the server responds with only that data. Here, the server acts more like a datastore and the client decides how to use the data and what to do with it.

### Properties of GraphQL
-----------------------------------------
- Query Language for APIs that allows the clients to request only the data they need, and nothing more
- It is built on top of HTTP and can be used with other transport protocols as well
- Single endpoint for all operations, which can simplify the API and reduce the number of round trips to the server
- Built-in support for real-time updates and subscriptions, which can be useful for applications that require real-time data
- Strongly typed schema that defines the structure of the data and the operations that can be performed on it, which can help with API evolution and versioning
- More flexigle in terms of API evolution, as the changes to the API might not require changes to the client code, as long as the schema is backward compatible
- Strongly typed schema means better validations and error handling, as the server can validate the incoming queries against the schema and return meaningful error messages if the query is invalid
- GrapQL has a powerful introspection system that allows clients to query the schema itself, which can be useful for building tools and documentation for the API and avoid the need for documentation frameworks like Swagger or OpenAPI.

### Core Data types in GraphQL
-----------------------------------------
- Scalar types: Int, Float, String, Boolean, ID.
- Object types: Custom types that can have fields of different types, including other object types.
- Enum types: A set of predefined values that a field can take.
- Interface types: A way to define a common set of fields that multiple object types can implement.
- Union types: A way to define a field that can return one of several different object types.
- Input types: A way to define the structure of the input data for mutations and queries.

### Scalar types in GraphQL
-------------------------------------
- String: Represents a sequence of characters. Example: "Hello, World!"
- Int: Represents a signed 32-bit integer. Example: 42
- Float: Represents a signed double-precision floating-point value. Example: 3.14
- Boolean: Represents a boolean value, either true or false. Example: true
- ID: Represents a unique identifier, often used for fetching an object or as the key for a cache. Example: "12345"

Example of a GraphQL query using scalar types:
```graphql
type User {
    id: ID!
    name: String!
    age: Int
    isActive: Boolean
    email: String
}
```
In this example, we have defined a User type with fields of different scalar types. The id field is of type ID and is marked as non-nullable (indicated by the !), meaning it must always have a value. The name field is of type String and is also non-nullable. The age field is of type Int and is nullable, meaning it can be null. The isActive field is of type Boolean and is nullable, and the email field is of type String and is nullable as well.

### Object types in GraphQL
-------------------------------------
Object types are custom types that can have fields of different types, including other object types. They are defined using the type keyword in the GraphQL schema.
```graphql
type User {
    id: ID!
    name: String!
    age: Int
    isActive: Boolean
    email: String
    address: Address
}
type Address {
    street: String
    city: String
    state: String
    zip: String
}
```
In this example, we have defined a User type that has a field called address, which is of type Address. The Address type is another object type that has its own fields (street, city, state, zip). This allows us to create complex data structures and relationships between different types in our GraphQL schema.

### Sets in GraphQL
-------------------------------------
GraphQL does not have a built-in set data type, but you can represent sets using lists (arrays) and custom logic in your resolvers. For example, you can define a list of unique items and ensure that duplicates are not allowed in your resolver functions.

```graphql
type User {
    id: ID!
    name: String!
    hobbies: [String]
}
```

### Question
-----------------------
How will you represent a User with multiple Posts in GraphQL?
You can represent a User with multiple Posts in GraphQL by defining a User type that has a field for posts, which is a list of Post objects. Here’s an example of how you can define this in your GraphQL schema:

```graphql
type Post {
    id: ID!
    title: String!
    content: String
}
type User {
    id: ID!
    name: String!
    email: String
    posts: [Post]
}
```
In this example, the User type has a field called posts, which is a list of Post objects

### Enum types in GraphQL
-------------------------------------
Enum types in GraphQL are a way to define a set of predefined values that a field can take. They are defined using the enum keyword in the GraphQL schema.

```graphql
enum Role {
    ADMIN
    USER
    GUEST
}
type User {
    id: ID!
    name: String!
    email: String
    role: Role
}
```

### Defining Queries in GraphQL
-------------------------------------
Query is at the heart of GraphQL and is the foundation of how the clients interact with the server.

A Query is a read-only operation that allows clients to request data from the server. It is defined using the query keyword in the GraphQL schema.

```graphql
type Query {
    user(id: ID!): User
    users: [User]
    post(id: ID!): Post
    posts: [Post]
}
```
In the above examples, we have defined a Query type with four fields. This is equivalent to defining four separate endpoints in a REST API. Each field represents a different query that clients can execute to fetch data from the server.

- user: This field takes an id argument of type ID and returns a User object.
- users: This field returns a list of all User objects.
- post: This field takes an id argument of type ID and returns a Post object.
- posts: This field returns a list of all Post objects.

To execute a query, clients can send a GraphQL query string to the server. For example, to fetch a user with a specific id, the client can send the following query:

```graphql
query {
    user(id: "123") {
        id
        name
        email
        role
    }
}
```
In this query, we are requesting the user with id "123" and specifying that **we want to retrieve the id, name, email, and role fields** of that user. The server will then respond with the requested data in the specified format (usually JSON).

### Nested Queries in GraphQL
-------------------------------------
GraphQL allows for nested queries, which means that you can request related data in a single query. For example, if you want to fetch a user along with their posts, you can do so in a single query:

```graphql
query {
    user(id: "123") {
        id
        namel
        role
        email
        posts {
            id
            title
            content
        }
    }
}
```
In the above query, we are fetching a user with id "123" and also requesting the posts associated with that user. The server will respond with the user data along with the list of posts, allowing us to retrieve all the necessary information in a single request. This is one of the key advantages of GraphQL over REST APIs, as it reduces the number of round trips to the server and allows clients to fetch exactly what they need.

Consider a usecase where an order has list of items, and each item has a product. In a REST API, we would have to make multiple requests to fetch the order, then the items, and then the products for each item. In GraphQL, we can fetch all of this data in a single request using nested queries:

```graphql
query {
    order(id: "123") {
        id
        total
        items {
            id
            quantity
            product {
                id
                name
                price
            }
        }
    }
}
```
In this query, we are fetching an order with id "123" and requesting the total, as well as the list of items associated with that order. For each item, we are also requesting the product details (id, name, price). This allows us to retrieve all the necessary information about the order, its items, and the products in a single request, which can significantly improve performance and reduce the complexity of the client code.

Also note that in the backend, the order entity might have a relationship with the item entity, and the item entity might have a relationship with the product entity. In a REST API, we would have to make separate requests to fetch each of these entities, but in GraphQL, we can fetch all of this data in a single request using nested queries. This is one of the key advantages of GraphQL over REST APIs, as it allows clients to fetch exactly what they need in a single request, reducing the number of round trips to the server and improving performance.

### How GrapQL exectures queries internally
---------------------------------------------------
When a GraphQL query is executed, the GraphQL server processes the query in several steps:
1. **Parsing**: The server parses the incoming query string to create an Abstract Syntax Tree (AST) representation of the query. This step checks for syntax errors and ensures that the query is well-formed.
2. **Validation**: The server validates the query against the GraphQL schema to ensure that it is semantically correct. This includes checking that the fields requested in the query exist in the schema, that the arguments provided are of the correct types, and that any required fields are included.
3. **Execution**: The server executes the query by fetching the requested data from the underlying data sources. This step involves resolving the fields and arguments in the query and retrieving the corresponding data.
4. **Response**: The server constructs a response based on the data retrieved during execution and sends it back to the client in the specified format (usually JSON).

The execution step is where the GraphQL server resolves each field in the query by calling the **appropriate resolver functions**. Resolvers are functions that are responsible for fetching the data for a specific field in the query. They can fetch data from databases, external APIs, or any other data source.
You can think of resolver functions as the equivalent of the controller functions in a REST API. They are responsible for handling the logic of fetching the data and returning it in the format expected by the GraphQL server.

Unlike in case of REST API's where the resoltion of the controller happens based on the path, here, it happends with the query schema.

## Setting up the project
---------------------------------------
1. Create a Spring boot project using Spring Initializr (https://start.spring.io/) with the following dependencies:
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- GraphQL Spring Boot Starter

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-graphql</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
```

### Create the packages in the spring boot project
----------------------------------------------------------
- model - entity classes that represent the data model of the application
- repository - interfaces that extend JpaRepository to provide CRUD operations for the entities
- resolver - classes that contain the resolver functions for the GraphQL queries and mutations
- service - classes that contain the business logic of the application and interact with the repositories to fetch and manipulate data
- config - classes that contain the configuration for the application, such as database configuration, GraphQL configuration, etc.
- exception - classes that contain custom exception handling logic for the application
- dto - classes that contain the data transfer objects (DTOs) for the application, which are used to transfer data between the client and the server.
- utils - classes that contain utility functions for the application, such as mapping functions, validation functions, etc.

## Hello-World with GraphQL
---------------------------------------
1. Create a simple GraphQL schema file (schema.graphqls) in the src/main/resources directory with the following content:

```graphql
type Query {
    hello: String
}
```
2. Create a resolver class (HelloResolver) in the resolver package with the following content:

```java
@Controller
public class HelloResolver{

    @QueryMapping
    public String hello(){
        return "Hello-world";
    }
```
3. Run the application and navigate to http://localhost:8080/graphiql to access the GraphiQL interface, which is a graphical interactive in-browser GraphQL IDE. You can execute the following query to see the result:

```graphql
query {
    hello
}
```
This will return the following response:

```json
{
    "data": {
        "hello": "Hello-world"
    }
}
``` 
Congratulations! You have successfully set up a simple GraphQL server and executed your first query. In the next steps, we will build a more complex application with multiple entities and relationships, and we will also explore mutations and subscriptions in GraphQL.

Now that we have seen to setup the query, lets move on with nested queries and how to fetch related data in a single query.

Whenever, we request the nested object, we should use the @SchemaMapping to map the fields under the object root.

### Data Transfer Objects in GraphQL
----------------------------------------------------
Often times, not all entities are mapped to the database tables and we need model objects to be mapped to our business needs. These do not have direct database representations and are populated at the application layer. They act like data carries within the application. By design they are **immutable**.
