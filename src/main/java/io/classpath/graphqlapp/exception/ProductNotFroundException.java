package io.classpath.graphqlapp.exception;

public class ProductNotFroundException extends RuntimeException{
    public ProductNotFroundException(Long id){
        super("Product not found with the id:: "+ id);
    }
}

