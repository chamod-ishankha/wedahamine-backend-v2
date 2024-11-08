package org.bytecub.WedahamineBackend.error;

import org.bytecub.WedahamineBackend.global.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestAlertException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestAlertException(BadRequestAlertException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setError(ex.getErrorKey());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationAlertException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationAlertException(AuthorizationAlertException ae, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setMessage(ae.getMessage());
        errorResponse.setError(ae.getErrorKey());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
