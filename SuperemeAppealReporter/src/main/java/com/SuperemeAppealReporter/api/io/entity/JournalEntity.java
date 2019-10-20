package com.SuperemeAppealReporter.api.io.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "journal")
public class JournalEntity extends BaseEntity{
	
	@Column(name = "journal_type", nullable = true)
	private String journalType;
	
	
	/**------------------------Mappings-------------------------**/

	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "journalEntity")
	private Set<CitationEntity> citationEntitySet;
}
