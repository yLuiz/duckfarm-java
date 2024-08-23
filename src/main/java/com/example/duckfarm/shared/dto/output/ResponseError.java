package com.example.duckfarm.shared.dto.output;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError {

    private String message;
    private HttpStatusCode httpStatus;
}
