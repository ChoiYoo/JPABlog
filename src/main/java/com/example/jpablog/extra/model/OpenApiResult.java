package com.example.jpablog.extra.model;

import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class OpenApiResult {

    private OpenApiResultResponse response;
}