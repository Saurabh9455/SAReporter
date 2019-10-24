package com.SuperemeAppealReporter.api.constant;

public interface ErrorConstant {

	public interface AuthenticationError
	{
		String USER_NOT_FOUND_ERROR_MESSAGE = "User does not exist";
		
	}
	
	public interface ExpiredJwtTokenError
	{
		String ERROR_CODE = "101";
		String ERROR_TYPE = "JWT ERROR";
		String ERROR_MESSAGE = "The Token is Expired.";
	}
	public interface UnsupportedJwtTokenError
	{
		String ERROR_CODE = "102";
		String ERROR_TYPE = "JWT ERROR";
		String ERROR_MESSAGE = "The Token is in wrong format.";
	}
	
	public interface MalformedJwtTokenError
	{
		String ERROR_CODE = "103";
		String ERROR_TYPE = "JWT ERROR";
		String ERROR_MESSAGE = "The Token is invalid/malformed.";
	}
	
	public interface JwtTokenNotPresentError
	{
		String ERROR_CODE = "104";
		String ERROR_TYPE = "JWT ERROR";
		String ERROR_MESSAGE = "The Token is not present/missing.";
	}
	
	public interface JwtTokenParsingError
	{
		String ERROR_CODE = "105";
		String ERROR_TYPE = "JWT Parsing ERROR";
		String ERROR_MESSAGE = "Error while parsing token in Authorization Filter ";
	}
	
	public interface InvalidCredentialError
	{
		String ERROR_CODE = "106";
		String ERROR_TYPE = "Invalid Credential ERROR";
		String ERROR_MESSAGE = "Invalid Credentials. Userame or password is not correct";
	}
	
	public interface UserAlreadyExistsError
	{
		String ERROR_CODE = "107";
		String ERROR_TYPE = "User Already Exists ERROR";
		String ERROR_MESSAGE = "The email is already registered with a differet account. Please enter another email";
	}
	
	public interface InputValidationError
	{
		String ERROR_CODE = "108";
		String ERROR_TYPE = "Input Validation ERROR";
        String ERROR_MESSAGE = "";
		
	}
	
	public interface EmailNotVerifiedError
	{
		String ERROR_CODE = "109";
		String ERROR_TYPE = "Email not verified ERROR";
		String ERROR_MESSAGE = "Please verify your email to login";
	}
	
	public interface SendingEmailError
	{
		String ERROR_CODE = "110";
		String ERROR_TYPE = "Email send failed ERROR";
		String ERROR_MESSAGE = "Unable to send email at this time.Please try again later";
	}
	
	public interface UserDoesNotExistError
	{
		String ERROR_CODE = "111";
		String ERROR_TYPE = "User does not exists ERROR";
		String ERROR_MESSAGE = "There is no account registered with the provided email address";
	}
	
	public interface InvalidPasswordResetTokenError
	{
		String ERROR_CODE = "112";
		String ERROR_TYPE = "Invalid password reset token ERROR";
		String ERROR_MESSAGE = "The password reset link is either invalid or broken";
	}
	
	public interface PasswordResetTokenExpiredError
	{
		String ERROR_CODE = "113";
		String ERROR_TYPE = "Password reset token expired ERROR";
		String ERROR_MESSAGE = "The password reset link is expired";
	}
	
	
	public interface InvalidEmailVerificationTokenError
	{
		String ERROR_CODE = "114";
		String ERROR_TYPE = "Invalid email verification token ERROR";
		String ERROR_MESSAGE = "The email verification link is either invalid or broken";
	}
	
	public interface EmailVerificationTokenExpiredError
	{
		String ERROR_CODE = "115";
		String ERROR_TYPE = "Email verification token expired ERROR";
		String ERROR_MESSAGE = "The email verification link is expired";
	}
	
	public interface InvalidClientCategoryForGetClientListError
	{
		String ERROR_CODE = "116";
		String ERROR_TYPE = "Invalid client category ERROR";
		String ERROR_MESSAGE = "Please select a valid client category [\"ALL\", \"INACTIVE\",\"ACTIVE\"]";
	}
	
	public interface InternalServerError
	{
		String ERROR_CODE = "500";
		String ERROR_TYPE = "Internal server ERROR [Error while executing Service/Dao/Repo method] : ";
		String ERROR_MESSAGE = "Error Description  : ";
	}
	public interface InvalidStaffCategoryForGetStaffListError
	{
		String ERROR_CODE = "200";
		String ERROR_TYPE = "Invalid Staff category ERROR";
		String ERROR_MESSAGE = "Please select a valid Staff category [\"ALL\", \"INACTIVE\",\"ACTIVE\"]";
	}
	public interface InvalidStaffIdError
	{
		String ERROR_CODE = "201";
		String ERROR_TYPE = "Invalid staff id ERROR";
		String ERROR_MESSAGE = "This staff user does not exist";
		String INVALID_STAFF_ERROR_MESSAGE = "This user does not belongs to any staff user.";
		String STAFF_DEACTIVATED_ERROR_MESSAGE = "Staff already deactivated.";
	}
	public interface InvalidClientIdError
	{
		String ERROR_CODE = "202";
		String ERROR_TYPE = "Invalid client id ERROR";
		String ERROR_MESSAGE = "This client user does not exist";
		String INVALID_CLIENT_ERROR_MESSAGE = "This client does not belongs to user.";
		String CLIENT_DEACTIVATED_ERROR_MESSAGE = "Client already deactivated.";
	}

	public interface FileUploadError
	{
		String ERROR_CODE = "117";
		String ERROR_TYPE = "File Upload ERROR";
		String ERROR_MESSAGE = "Unable to upload case file.";
		
		String ERROR_MESSAGE_INVALID_DOCID = "No case registered with the DocId given to upload PDF file";

	}
	public interface InvalidPlanTypeError
	{
		String ERROR_CODE = "203";
		String ERROR_TYPE = "Invalid plan type ERROR";
		String ERROR_MESSAGE = "Please select a valid plan type.";
	}
	public interface InvalidPlanIdError
	{
		String ERROR_CODE = "204";
		String ERROR_TYPE = "Invalid plan id ERROR";
		String ERROR_MESSAGE = "Plan id does not exists.";
		String PLAN_DEACTIVATED_ERROR_MESSAGE = "This plan is already deactivated";

	}
	

	public interface InvalidCourtIdError
	{
		String ERROR_CODE = "205";
		String ERROR_TYPE = "Invalid court id ERROR";
		String ERROR_MESSAGE = "Court id does not exists.";
		String PLAN_DEACTIVATED_ERROR_MESSAGE = "This plan is already deactivated";
	}

	public interface InvalidCourtBranchIdError
	{
		String ERROR_CODE = "206";
		String ERROR_TYPE = "Invalid court brnach id ERROR";
		String ERROR_MESSAGE = "Court branch does not exists.";
		String PLAN_DEACTIVATED_ERROR_MESSAGE = "This plan is already deactivated";

	public interface GetPdfFileError
	{
		String ERROR_CODE = "118";
		String ERROR_TYPE = "Get PDF ERROR";
		String ERROR_MESSAGE = "No pdf file is present for the given case.Please try with difference docId";
	}
	
}
