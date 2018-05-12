package moneytransfer.rest;

import moneytransfer.services.NotEnoughMoneyException;
import moneytransfer.services.UnknownAccountException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    public static class ErrorMessage {
        public String type;
        public String message;
    }

    @Override
    public Response toResponse(Throwable ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.message = ex.getMessage();
        errorMessage.type = ex.getClass().getSimpleName();

        return Response.status(getStatus(ex))
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private int getStatus(Throwable ex) {
        if (ex instanceof NotEnoughMoneyException) {
            return 550;
        } else if (ex instanceof UnknownAccountException) {
            return 404;
        } else {
            return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        }
    }
}