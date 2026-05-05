## Mutations in GraphQL
------------------------------------
In GraphQL, mutations are used to modify the data on the server. Creation, updation and deletion fall into this category. They are defined using the "mutation" keyword in the GraphQL schema. A mutation consists of a name, set of fields that specify the data to be modified and the return type. Each field can have arguments that allow for specifying the data to be modified.

Example of a mutation in GraphQL:
```graphql
type Mutation {
  createUser(name: String!, email: String!): User
  updateUser(id: ID!, name: String, email: String): User
  deleteUser(id: ID!): Boolean
}
```
One importation point to remember is that there can be only one query block and one mutation block in a GraphQL schema. This means that all the mutations must be defined within a single mutation block.

### Subscriptions in GraphQL
------------------------------------
Subscriptions in GraphQL are used to enable real-time communication between the client and the server. They allow clients to subscribe to specific events or data changes on the server, and receive updates whenever those events occur. Subscriptions are defined using the "subscription" keyword in the GraphQL schema. This way, the clients can receive real-time updates without having to continuously poll the server for changes. This is a push based model unlike queries and mutations which are pull based.

### Setting up a Subscription in GraphQL with Spring Boot
------------------------------------------------------------------
1. Add the necessary dependencies for GraphQL and WebSocket in your Spring Boot project.
2. Add the dependency of webflux to your project to enable reactive programming.
3. Define the subscription in your GraphQL schema.
```graphql
type Subscription {
  userCreated: User
}
```
4. Implement the subscription resolver in your Spring Boot application.
```java
@Component
public class SubscriptionResolver implements GraphQLSubscriptionResolver {

    private final Flux<User> userCreatedFlux;

    public SubscriptionResolver() {
        this.userCreatedFlux = Flux.create(emitter -> {
            // Logic to emit new users when they are created
        });
    }

    public Flux<User> userCreated() {
        return userCreatedFlux;
    }
}
```5. Set up the WebSocket configuration to enable subscriptions.
```java
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new GraphQLWebSocketHandler(), "/subscriptions")
                .setAllowedOrigins("*");
    }
}
```
6. Start the Spring Boot application and test the subscription using a GraphQL client that supports subscriptions, such as GraphiQL or Apollo Client.

## Flow
1. The client sends a subscription request to the server.
2. The server processes the subscription request and establishes a WebSocket connection with the client.
3. Whenever the specified event occurs on the server (e.g., a new user is created), the server sends an update to the client through the WebSocket connection.
4. The client receives the update and can react accordingly, such as updating the UI to display the new user information.

Sink = Event producer (You push the data manually to the sink, ex: creating the order)
Flux = Event Stream (subscribers consume the data continously as it is producer by the sink, ex: order created, order updated, order deleted)
Subscription = Bridge between the event producer and the event stream (it allows clients to subscribe to the event stream and receive updates in real-time)

### Validation
---------------------------------------------------------------------
In GraphQL, validation is done at 3 levels. One at the schema level and another at the resolver level.
1. Schema Level Validation: This is done by defining the types and fields in the GraphQL schema. The schema defines the structure of the data and the operations that can be performed on it. The GraphQL server will validate the incoming queries and mutations against the schema to ensure that they are well-formed and adhere to the defined types and fields.
2. Resolver Level Validation: This is done in the resolver functions that handle the queries and mutations. The resolver functions can perform additional validation on the input data before processing it. For example, you can check if the required fields are present, if the data types are correct, or if the user has the necessary permissions to perform the operation.
3. Validation can also be done at the application layer using spring validation framework. This is primarily done for data sanitization and to ensure that the data being processed is valid and does not cause any issues in the application. Ex:
```java
@NotNull
@Size(min = 3, max = 50)
@NotEmpty
@Email
```

Steps to add validation in GraphQL with Spring Boot:
1. Add the dependency (spring-boot-starter-validation) to your Spring Boot project.
2. Define the validation constraints in your GraphQL schema using directives or annotations.
3. Implement the validation logic in your resolver functions or service layer.
   **Note**:
- It is important to note that the field level annotation should be applied at the DTO level and not at the entity level. This is because the DTO is responsible for receiving the input data from the client, and it is the appropriate place to validate that data before it is processed further in the application.
- Applying validation annotations at the entity level can lead to issues when the entity is used in different contexts, such as when it is being persisted to the database or when it is being used in other parts of the application where the validation constraints may not be relevant.

## Exception handling in GraphQL
------------------------------------
Unlike in case of REST APIs where the client can infer the error based on the HTTP status code (400series - client side, 500 series - server side), in GraphQL, all the requests are POST requests to a single endpoint, and the response always returns a 200 status code, even in case of errors.

Instead of relying on HTTP status codes, GraphQL uses a structured error response to communicate errors to the client. The error response contains an "errors" field that provides details about the error, including a message, locations in the query where the error occurred, and any additional information relevant to the error.
```json
{
  "data": null,
  "errors": [
    {
      "message": "User not found",
      "locations": [
        {
          "line": 2,
          "column": 3
        }
      ],
      "path": [
        "user"
      ]
    }
  ]
}
```
**Note**: The errors field is an array and also include **locations** section containing the line numbers and column numbers where the error occurred in the query, and a **path** section that indicates the path to the field that caused the error. This structured error response allows clients to handle errors in a consistent way, regardless of the underlying cause of the error. This is purely for debugging purposes and should not be used for any business logic in the client application.

To Handle exceptions in GraphQL with Spring Boot, create ExceptionConfig class and define a bean called ```DataFetcherExceptionHandler``` that will handle the exceptions thrown by the resolvers. This handler will be responsible for catching the exceptions and returning a structured error response to the client.
```java
@Configuration
public class ExceptionConfig {

    @Bean
    public DataFetcherExceptionHandler dataFetcherExceptionHandler() {
        return new CustomDataFetcherExceptionHandler();
    }
}
```


### Application Security in GraphQL
-------------------------------------
Unlike Application security in REST APIs where we can secure the endpoints using the HTTP method and the URL pattern (i.e /api/users/**, /api/orders/**, etc) and also using HTTP methods (GET, POST, PUT, DELETE, etc), in GraphQL, all the requests are sent to a single endpoint (e.g /graphql) and the operations are defined in the query or mutation. This means that we cannot secure the endpoints based on the URL pattern or the HTTP method.

To secure a GraphQL API, we need to implement security at the entry point (i.e, resolver level). This means that we need to check the user's authentication and authorization before allowing them to access the data or perform the operations defined in the resolvers. You can think for security based on resolver level as a fine grained security.

### In Application Security:
- Authentication is the process of verifying the identity of a user, while Authorization is the process of determining whether a user has the necessary permissions to access a resource or perform an action. In GraphQL, we can implement both authentication and authorization at the resolver level to ensure that only authenticated and authorized users can access the data and perform the operations defined in the resolvers.
  There are different types of Authentication but all of them resolve to if the user is authenticated or not. Some of the common authentication methods include:
1. Token-based authentication: This involves generating a token (e.g., JWT) upon successful
   authentication, which the client can then include in the headers of subsequent requests to authenticate themselves.
2. Session-based authentication: This involves creating a session for the user upon successful authentication, and storing the session information on the server. The client can then include the session ID in the headers of subsequent requests to authenticate themselves.
3. OAuth: This is a widely used authentication protocol that allows users to authenticate using their existing credentials from a third-party service (e.g., Google, Facebook, etc). The client can obtain an access token from the third-party service and include it in the headers of subsequent requests to authenticate themselves.


- Authorization can be implemented using role based access control (RBAC) or attribute based access control (ABAC) to restrict access to certain fields or operations based on the user's role or attributes. This way, we can ensure that only users with the appropriate permissions can access sensitive data or perform critical operations in the GraphQL API.
  Users, Groups, Roles and Permissions


### Implementing security in GraphQL with Spring Boot:
1. Add the necessary dependencies for Spring Security in your Spring Boot project. (spring-boot-starter-security)
2. Register the users to the application using in-memory authentication or by connecting to a database.
```java
@Bean
public UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername("user").password("{noop}password").roles("USER").build());
    manager.createUser(User.withUsername("admin").password("{noop}password").roles("ADMIN").build());
    return manager;
}
```

### Packaging and Deployment of GraphQL API
-------------------------------------
GraphQL APIs can be packaged and deployed in various ways, depending on the requirements of the application and the infrastructure being used. Some common approaches for packaging and deploying GraphQL APIs include:
1. Containerization: GraphQL APIs can be packaged as Docker containers, which can then be
   deployed to container orchestration platforms like Kubernetes or Docker Swarm. This approach allows for easy scalability and management of the GraphQL API.
2. Serverless Deployment: GraphQL APIs can be deployed as serverless functions using platforms like
   AWS Lambda, Azure Functions, or Google Cloud Functions. This approach allows for automatic scaling and reduced operational overhead.
3. Traditional Deployment: GraphQL APIs can also be deployed on traditional web servers or application servers, such as Apache Tomcat, Nginx, or Spring Boot's embedded server. This approach is suitable for applications that require more control over the deployment environment and may not be suitable for containerization or serverless deployment.

### Packaging as Docker image
==================================================
1. Write a Dockerfil with multi-stage build process
2. Create a docker image
```
docker build -t orders-app .
```
3. List the docker images ```docker images```
4. Create a container from the image ``` docker container run -f orders-app```
5. List the container ```docker container ls ```
6. Log into the container ```docker container exec -it <container-id> /bin/bash```
7. Execute the Graphql Curl command
```
curl -X POST -H "Content-Type: application/json" -d '{"query": "{ orders { id, product, quantity } }"}' http://localhost:8080/graphql
```
Sample response:
```json
{
  "data": {
    "orders": [
      {
        "id": "1",
        "product": "Product A",
        "quantity": 2
      },
      {
        "id": "2",
        "product": "Product B",
        "quantity": 1
      }
    ]
  }
}
```

### N+1 problem
-----------------------------------------------
When we have nested datastructure having one-to-* relationships, fetching 1 order and then for each order we might have to fetch all the line items leading to multiple DB calls which are very resource intensive tasks

```
query {
    orders {
        id
        customerName
        items {
            productId
            qty
        }
    }
}
```
5 orders and each having one line items
Internally 1 query to fetch orders and 5 queries to fetch items each time if we have 5 order

### Fix
---------------------------------
1. Batch all the orderIds and fetch all in ONE DB call
2. Use the DataLoader to fetch and return the list of all the lineitems for all the order IDs.
3. Optinoally optimize using CompletableFuture
4. Register the DataLoad in the registry
