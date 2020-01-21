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
@Table(name="secondary_citation")
public class SecondaryCitationEntity extends BaseEntity {

	
	@Column(name = "page_number", nullable = true)
	private String pageNumber;
	
	
	
	@Column(name = "year", nullable = true)
	private String year;
	

	/**------------------------Mappings-------------------------**/
	
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private CitationCategoryEntity citationCategoryEntity;
    
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private JournalEntity journalEntity;
	
}
