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
@Table(name="citation")
public class CitationEntity extends BaseEntity {

	
	@Column(name = "page_number", nullable = true)
	private int pageNumber;
	
	
	
	@Column(name = "year", nullable = true)
	private int year;
	
	
	
	@Column(name = "other_citation", nullable = true)
	private String otherCitation;
	

	/**------------------------Mappings-------------------------**/
	
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private CitationCategoryEntity citationCategoryEntity;
    
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private JournalEntity journalEntity;
	
}
