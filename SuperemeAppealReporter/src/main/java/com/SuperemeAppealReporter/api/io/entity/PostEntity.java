package com.SuperemeAppealReporter.api.io.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="post_entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity extends BaseEntity {

	private String heading;
	private String body;
	private String  linkedDocId;
	
	
	
}
