package com.peryite.journeyd3.models;

import lombok.Data;
import lombok.NoArgsConstructor;

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
