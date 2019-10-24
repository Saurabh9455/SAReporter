package com.SuperemeAppealReporter.api.shared.dto;

import java.util.List;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourtDto {
	private int courtId;
	private String courtType;
	private List<CourtBranchDto> courtBranchDto;
}
