package com.SuperemeAppealReporter.api.bo;

import javax.mail.Multipart;

import org.springframework.web.multipart.MultipartFile;

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
public class UploadCasePdfBo {

	private MultipartFile file;
}
