package com.SuperemeAppealReporter.api.pojo;

import java.util.Map;

import com.SuperemeAppealReporter.api.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mail {

    private String from;
    private String to;
    private String subject;
    private Map<String,Object> model;
    private UserType belongsTo;
    
  
}
