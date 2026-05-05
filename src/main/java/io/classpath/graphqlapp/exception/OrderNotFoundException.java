package io.classpath.graphqlapp.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id){
        super("Order not found with the id:: "+ id);
    }
}
