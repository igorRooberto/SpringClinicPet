package com.example.SpringClinicPet.handler;

import com.example.SpringClinicPet.handler.error.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorMessage> handleGenericException(Exception ex) {

        logger.error("Erro inesperado: ", ex);

        ErrorMessage error = new ErrorMessage("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro interno no servidor.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    private ResponseEntity<ErrorMessage> handleSpringSecurityAccessDenied(org.springframework.security.access.AccessDeniedException ex) {
        ErrorMessage error = new ErrorMessage("Forbidden", HttpStatus.FORBIDDEN.value(), "Acesso negado. Você não tem permissão de ADMIN para esta ação.");
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

}
