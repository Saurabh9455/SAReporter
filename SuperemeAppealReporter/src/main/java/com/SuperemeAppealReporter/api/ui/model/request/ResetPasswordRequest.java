package com.SuperemeAppealReporter.api.ui.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
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
public class ResetPasswordRequest {

	    @NotBlank(message = "Email should not be blank")
		@Email(message = "Email is not valid")
		private String userEmail;
		
		@NotBlank(message = "Password should not be blank")
		@Length(max = 15, min = 8, message = "Password should be minimum 8 characters long or maximum 15 characters long"  )
		private String userPassword;
		
}
