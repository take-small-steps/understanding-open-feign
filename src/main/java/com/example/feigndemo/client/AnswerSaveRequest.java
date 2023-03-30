package com.example.feigndemo.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AnswerSaveRequest {
    private String af_counsel_no;
    private String af_expert_id;
    private String answer_contents;


    String printS(){
        return "Xx";
    }
}
