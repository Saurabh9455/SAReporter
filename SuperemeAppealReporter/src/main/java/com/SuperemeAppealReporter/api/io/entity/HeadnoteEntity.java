package com.SuperemeAppealReporter.api.io.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "head_note")
public class HeadnoteEntity extends BaseEntity{

	
	@Column(name = "actname_1", nullable = true)
	private String actname1;
	
	@Column(name = "actname_2", nullable = true)
	private String actname2;
	
	@Column(name = "actname_3", nullable = true)
	private String actname3;
	
	@Column(name = "section_1", nullable = true)
	private String section1;
	
	@Column(name = "section_2", nullable = true)
	private String section2;
	
	@Column(name = "section_3", nullable = true)
	private String section3;
	
	@Column(name = "topic", nullable = true)
	private String topic;
	
	@Column(name = "priority", nullable = true)
	private String priority;
	
	@Column(name = "headnote", nullable = true)
	private String headnote;
	
	@Column(name = "paragrap", nullable = true)
	private int paragraph;
	
	
	
	
	/**------------------------Mappings-------------------------**/
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private CaseEntity caseEntity;
	
	
}
