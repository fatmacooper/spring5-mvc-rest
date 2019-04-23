package com.orionsson.spring5mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    @ApiModelProperty(value = "First Name", required = true)
    private String firstname;
    @ApiModelProperty(required = true)
    private String lastname;
    @JsonProperty("customer_url")
    private String customerUrl;
}
