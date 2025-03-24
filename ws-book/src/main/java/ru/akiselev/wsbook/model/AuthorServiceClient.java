package ru.akiselev.wsbook.model;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.akiselev.wsbook.dto.AuthorDTO;

@FeignClient(name = "ws-author", fallback = AuthorFallback.class)
public interface AuthorServiceClient {

    @GetMapping("/authors/{id}")
    @HystrixCommand(fallbackMethod = "getAuthor")
    AuthorDTO getAuthor(@PathVariable("id") Long id);
}

@Component
class AuthorFallback implements AuthorServiceClient{
    @Override
    public AuthorDTO getAuthor(@PathVariable("id") Long id) {
        return AuthorDTO.builder().id(100L).build();
    }
}