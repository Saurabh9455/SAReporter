package com.SuperemeAppealReporter.api.shared.dto;

import com.SuperemeAppealReporter.api.enums.TokenType;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VerificationTokenDto {

    private TokenType tokenType;
    private String confirmationToken;
    private UserEntity userEntity;
}
