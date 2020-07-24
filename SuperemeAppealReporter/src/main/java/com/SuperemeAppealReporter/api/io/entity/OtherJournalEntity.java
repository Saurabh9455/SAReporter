package com.SuperemeAppealReporter.api.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "other_journal")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtherJournalEntity extends BaseEntity {

	@Column(name = "journal_type", nullable = true)
	private String journalType;
	
	
}
