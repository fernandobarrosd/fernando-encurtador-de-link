package br.com.fernando.fernando_encurtador_de_link.handlers;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable throwable, DataFetchingEnvironment env) {
        if (throwable instanceof ConstraintViolationException) {
            return GraphQLError.newError()
            .errorType(ErrorType.BAD_REQUEST)
            .message("Validation fields error")
            .path(env.getExecutionStepInfo().getPath())
            .location(env.getField().getSourceLocation())
            .build();
        }
        return null;
    }
    
}