package com.peryite.journeyd3.models;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
public class Chapter {
    private int id;
    @NonNull
    private String name;
    private ArrayList<Task> tasks;
    private Reward reward;


}
