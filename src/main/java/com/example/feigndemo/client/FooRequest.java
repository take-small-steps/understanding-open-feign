package com.example.feigndemo.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class FooRequest {
    private String usr_id;
    private String usr_name;
    private String usr_email;
    private String usr_phone;
    private String expert_type;
    private String category;
    private String title;
    private String contents;
    private String answer_title;
    private String answer_contents;
    private String af_counsel_no;
    private String af_expert_id;
}
