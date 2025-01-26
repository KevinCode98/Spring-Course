package pvin.curso.springboot.sprinbooterror.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Error {
    private String message;
    private String error;
    private int status;
    private Date date;
}
