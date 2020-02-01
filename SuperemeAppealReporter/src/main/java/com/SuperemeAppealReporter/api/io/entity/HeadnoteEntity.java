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
	
	@Column(name = "actname_4", nullable = true)
	private String actname4;
	
	@Column(name = "section_1", nullable = true)
	private String section1;
	
	@Column(name = "section_2", nullable = true)
	private String section2;
	
	@Column(name = "section_3", nullable = true)
	private String section3;

	@Column(name = "section_4", nullable = true)
	private String section4;
	
	@Column(name = "topic", nullable = true)
	private String topic;
	
	@Column(name = "priority", nullable = true)
	private String priority;
	
	@Column(name = "headnote", nullable = true)
	private String headnote;
	
	@Column(name = "paragrap", nullable = true)
	private String paragraph;
	
	@Column(name = "order_1", nullable = true)
	private String order1;
	
	@Column(name = "order_2", nullable = true)
	private String order2;
	
	@Column(name = "order_3", nullable = true)
	private String order3;
	
	@Column(name = "order_4", nullable = true)
	private String order4;
	
	@Column(name = "article_1", nullable = true)
	private String article1;
	
	@Column(name = "article_2", nullable = true)
	private String article2;
	
	@Column(name = "article_3", nullable = true)
	private String article3;
	
	@Column(name = "article_4", nullable = true)
	private String article14;
	
	@Column(name = "rule_1", nullable = true)
	private String rule1;
	
	@Column(name = "rule_2", nullable = true)
	private String rule2;
	
	@Column(name = "rule_3", nullable = true)
	private String rule3;
	
	@Column(name = "rule_4", nullable = true)
	private String rule4;
	
	
	
	
	
	
	/**------------------------Mappings-------------------------**/

	@ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
	private CaseEntity caseEntity;
	
	
}
