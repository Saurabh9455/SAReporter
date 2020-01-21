package com.SuperemeAppealReporter.api.ui.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor

public class AddCaseMasterResponse {

  
  List<GetCourtDropDownResponse> journalDropDown;
  List<GetCourtDropDownResponse> courtBenchDropDown;
  List<GetCourtDropDownResponse> citationCategoryDropdown;
  List<GetCourtDropDownResponse> caseCategoryDropdown;
  
  
  
  
}
