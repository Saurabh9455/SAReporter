package com.SuperemeAppealReporter.api.io.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@Column(name = "id",nullable = false, updatable = false,insertable = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	
	@JsonIgnore
	@Column(name = "created_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdDate;

	
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@Column(name = "updated_date", nullable = false, updatable = true)
	private Date updatedDate ;
	
	@JsonIgnore
	@CreatedBy
	@Column(name = "created_by", nullable = false, updatable = false)
	private String createdBy = "system";
	
	@JsonIgnore
	@LastModifiedBy
	@Column(name = "updated_by",nullable = false,updatable = true)
	private String updatedBy = "system";
	
	@JsonIgnore
	@Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN", updatable = true)
	private Boolean active = true;

}
