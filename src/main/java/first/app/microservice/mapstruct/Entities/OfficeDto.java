package first.app.microservice.mapstruct.Entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class OfficeDto {

    @JsonProperty("id")
    private Long id;


    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;


    @JsonProperty("provider")
    private String provider;


    @JsonProperty("inactive")
    private boolean inactive;


}
