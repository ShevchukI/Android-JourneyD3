package com.peryite.journeyd3.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
//@ToString
//@EqualsAndHashCode
@NoArgsConstructor
public class Conquest {
    private int id;
    private String name;
    private String description;
    private boolean done;
}
