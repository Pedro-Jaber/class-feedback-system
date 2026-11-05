package br.com.pedrojaber.handlers;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import jakarta.validation.ValidationException;

public class GlobalExceptionMapper {

    @ServerExceptionMapper
    public RestResponse<String> handleException(Exception ex) {
        System.out.println(ex.getMessage());

        return RestResponse.status(RestResponse.Status.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ServerExceptionMapper
    public RestResponse<String> handleException(ValidationException ex) {
        System.out.println(ex.getMessage());

        return RestResponse.status(RestResponse.Status.BAD_REQUEST, ex.getMessage());
    }

    @ServerExceptionMapper
    public RestResponse<String> handleException(IllegalArgumentException ex) {
        System.out.println(ex.getMessage());

        return RestResponse.status(RestResponse.Status.BAD_REQUEST, ex.getMessage());
    }

}
