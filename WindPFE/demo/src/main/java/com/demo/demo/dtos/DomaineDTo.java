package com.demo.demo.dtos;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
public class DomaineDTo {
    public long id;
    public String lang;
    public String version ;
    public String name ;
}
