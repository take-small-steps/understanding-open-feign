package com.example.feigndemo.client;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class FooResponse {
    private int userId;
    private int id;
    private String title;
    private boolean completed;
}