package first.app.microservice.model;


import first.app.microservice.annotation.Luhn;
import first.app.microservice.enums.Shops;
import jdk.jfr.Experimental;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_office")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true , updatable = true)
    @Size(min = 6 , max = 29)
    @NotNull
    @Pattern(regexp = "^[A-Za-z]+")
    private String name;

    @Column(unique = true , updatable = false)
    @Size(min = 12)
    @NotNull
    @Pattern(regexp = "[0-9]+")
    @Luhn
    private String code;

    @Column(updatable = true)
    @Enumerated(EnumType.STRING)
    private Shops provider;

    @Column(updatable = true)
    private boolean inactive;

}
