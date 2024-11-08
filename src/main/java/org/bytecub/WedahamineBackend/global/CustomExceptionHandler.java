package org.bytecub.WedahamineBackend.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        // Collect all field errors into a single message
        String message = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.debug("Validation errors: {}", message);

        // Prepare the error response
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setMessage("VALIDATION FAILED!");
        errorResponse.setError(message);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
