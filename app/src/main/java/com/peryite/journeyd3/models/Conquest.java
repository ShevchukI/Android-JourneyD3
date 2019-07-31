package com.peryite.journeyd3.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Conquest {
    private long id;
    private String name;
    private String description;
    private boolean done;
}
