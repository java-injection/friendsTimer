package it.cascella.friendstimer.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // Gestione personalizzata delle eccezioni di validazione
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // L'annotazione @ExceptionHandler indica che questo metodo
    //gestirà le eccezioni di tipo MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp= now.format(formatter);
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Bad Request");
        errors.put("status", "400");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        errors.put("timestamp",timestamp);
        return errors;
    }




    // Puoi aggiungere altri metodi per gestire altre eccezioni qui
}
