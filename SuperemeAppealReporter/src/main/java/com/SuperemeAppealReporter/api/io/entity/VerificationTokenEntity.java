package com.SuperemeAppealReporter.api.io.entity;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.SuperemeAppealReporter.api.enums.TokenType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "verification")
public class VerificationTokenEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
	@Column(name = "token_type")
    private TokenType tokenType;
   
    
	@Column(name = "confirmation_token", nullable = false, updatable = true)
    private String confirmationToken;


    @ManyToOne(fetch = FetchType.LAZY,cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private UserEntity userEntity;
    
    public VerificationTokenEntity() {
		super();
	}

	public VerificationTokenEntity(UserEntity employerEntity) {
        this.userEntity = employerEntity;
        confirmationToken = UUID.randomUUID().toString()+"$"+employerEntity.getEmail();
    }
    

    
}