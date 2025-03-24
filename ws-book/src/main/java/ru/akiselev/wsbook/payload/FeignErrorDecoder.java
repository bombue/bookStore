package ru.akiselev.wsbook.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.akiselev.wsbook.exceptions.AuthorNotFoundException;

import java.io.IOException;
import java.io.InputStream;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage exceptionMessage = null;
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            exceptionMessage = mapper.readValue(bodyIs, ExceptionMessage.class);
         } catch (IOException e) {
            return new Exception(e.getMessage());
        }

        return switch (response.status()) {
            case 400 -> new RuntimeException(exceptionMessage.getMessage() != null ? exceptionMessage.getMessage() : "Bad request");
            case 404 -> new RuntimeException(exceptionMessage.getMessage() != null ? exceptionMessage.getMessage() : "Not found");
            default -> new Exception(response.reason());
        };

    }
}
