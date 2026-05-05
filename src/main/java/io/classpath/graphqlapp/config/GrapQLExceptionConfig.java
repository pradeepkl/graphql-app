package io.classpath.graphqlapp.config;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import io.classpath.graphqlapp.exception.OrderNotFoundException;
import io.classpath.graphqlapp.exception.ProductNotFroundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.graphql.execution.ErrorType;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class GrapQLExceptionConfig {

    @Bean
    public DataFetcherExceptionResolver exceptionResolver(){
        return ((exception, environment) -> {
            System.out.println(exception.getMessage());

            if(exception instanceof OrderNotFoundException){
                return Mono.just(List.of(buildError("order not found", "NOT_FOUND", environment, ErrorType.NOT_FOUND)));
            }

            if(exception instanceof ProductNotFroundException){
                return Mono.just(List.of(buildError("product not found", "NOT_FOUND", environment, ErrorType.NOT_FOUND)));
            }
            return Mono.just(List.of(buildError("Internal server error ", "INTERNAL_ERROR", environment, ErrorType.INTERNAL_ERROR)));
        });
    }


    //Define the format of the errors to be sent to the client
    private GraphQLError buildError(String message, String code, DataFetchingEnvironment environment, ErrorType errorType) {
        Map<String, Object> extensions = new LinkedHashMap<>();
        extensions.put("code", code);
        extensions.put("timestamp", Instant.now().toString());

        return GraphqlErrorBuilder.newError(environment).message(message).errorType(errorType).extensions(Map.of("code", code, "timestamp", Instant.now().toString())).build();
    }
}
