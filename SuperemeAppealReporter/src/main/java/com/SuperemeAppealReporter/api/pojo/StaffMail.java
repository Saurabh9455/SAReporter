package com.SuperemeAppealReporter.api.pojo;

import java.util.Map;

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
public class StaffMail {
	private String from;
    private String to;
    private String subject;
    private Map<String,Object> model;
    private String belongsTo;
}
