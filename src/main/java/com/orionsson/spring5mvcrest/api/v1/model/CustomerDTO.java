package com.orionsson.spring5mvcrest.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String firstname;
    private String lastname;
    private String customerUrl;
}
