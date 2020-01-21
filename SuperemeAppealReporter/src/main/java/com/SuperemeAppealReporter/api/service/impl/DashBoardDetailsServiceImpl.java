package com.SuperemeAppealReporter.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.enums.PaymentStatus;
import com.SuperemeAppealReporter.api.enums.UserType;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.io.entity.CaseEntity;
import com.SuperemeAppealReporter.api.io.repository.CaseRepository;
import com.SuperemeAppealReporter.api.io.repository.PaymentRepository;
import com.SuperemeAppealReporter.api.io.repository.SubscriptionPlanRepository;
import com.SuperemeAppealReporter.api.io.repository.UserRepository;
import com.SuperemeAppealReporter.api.service.DashBoardService;
import com.SuperemeAppealReporter.api.ui.model.response.GetDashBoardResponse;

@Service
public class DashBoardDetailsServiceImpl implements DashBoardService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	CaseRepository caseRepository;
	
	@Autowired 
	SubscriptionPlanRepository subscriptionPlanRepository;
	
	@Override
	public GetDashBoardResponse getDashBoardDetailsService() {

		GetDashBoardResponse getDashBoardResponse = null;
		try
		{
		int totalOrders = paymentRepository.getTotalCountForPayments();
		int pendingOrders = paymentRepository.getTotalCountForSuccessPayments(PaymentStatus.PENDING.toString());
		int failedOrders = paymentRepository.getTotalCountForSuccessPayments(PaymentStatus.FAILED.toString());
		int completedOrders = paymentRepository.getTotalCountForSuccessPayments(PaymentStatus.SUCCESS.toString());
		
		int totalClients = userRepository.getTotalCountForUsersByType(UserType.USER.toString());
		int activeClients = userRepository.getTotalCountForUsersByStatus(UserType.USER.toString(),true);
		int inActiveClients = userRepository.getTotalCountForUsersByStatus(UserType.USER.toString(), false);
		
	    int totalStaff = userRepository.getTotalStaffUsers(UserType.USER.toString());
	    int adminStaff = userRepository.getTotalCountForUsersByType(UserType.ADMIN.toString());
	    int superAdminStaff = userRepository.getTotalCountForUsersByType(UserType.SUPER_ADMIN.toString());
	    int dataEntryOperatorStaff = userRepository.getTotalCountForUsersByType(UserType.DATA_ENTRY_OPERATOR.toString());
	    

	    int totalCases = caseRepository.getTotalCases();
	    int overRuledCases = caseRepository.getTotalOveruledCases();
	    int liveCases = caseRepository.getTotalLiveCases();
	    
	    int totalPlans = subscriptionPlanRepository.getTotalCountForPlan();
	    
	    double totalEarningThisWeek = 0.0;
	    double totalOverallEarning = paymentRepository.getTotalCountForSuccessPayments(PaymentStatus.SUCCESS.toString());
	   System.out.println("------------------------------------------------COUNT(*) FINISHED--------------");
/*	    List<CaseEntity> supremeCourtCaseEntities = caseRepository.getSupremeCourtCases();
	    List<CaseEntity> supremeCourtDivisionalBenchEntities = caseRepository.getSupremeCourtDivisionalBenchCases();
	    List<CaseEntity> supremeCourtFullBenchEntities = caseRepository.getSupremeCourtFullBenchCases();
	    List<CaseEntity> supremeCourtThirdBenchEntities = caseRepository.getSupremeCourtThirdBenchCases();
	    List<CaseEntity> highCourtEntities = caseRepository.getHighCourtCases();*/
	    
	  /*  int supremeCourtCases = supremeCourtCaseEntities.size();
	    int supremeCourtDivisionalBenchCases = supremeCourtDivisionalBenchEntities.size();
	    int supremeCourtFullBenchCases = supremeCourtFullBenchEntities.size();
	    int supremeCourtThirdBenchCases = supremeCourtThirdBenchEntities.size();
	    int highCourtCases = highCourtEntities.size();*/
	   
	   int supremeCourtCases = caseRepository.getSupremeCourtCases();
	    int supremeCourtDivisionalBenchCases = caseRepository.getSupremeCourtDivisionalBenchCases();
	    int supremeCourtFullBenchCases = caseRepository.getSupremeCourtFullBenchCases();
	    int supremeCourtThirdBenchCases = caseRepository.getSupremeCourtThirdBenchCases();
	    int highCourtCases = caseRepository.getHighCourtCases();;
	    
	    getDashBoardResponse = new GetDashBoardResponse();
	    getDashBoardResponse.setActiveClients(activeClients);
	    getDashBoardResponse.setAdminStaff(adminStaff);
	    getDashBoardResponse.setCompletedOrders(completedOrders);
	    getDashBoardResponse.setDataEntryOperatorStaff(dataEntryOperatorStaff);
	    getDashBoardResponse.setFailedOrders(failedOrders);
	    getDashBoardResponse.setInActiveClients(inActiveClients);
	    getDashBoardResponse.setLiveCases(liveCases);
	    getDashBoardResponse.setOverRuledCases(overRuledCases);
	    getDashBoardResponse.setPendingOrders(pendingOrders);
	    getDashBoardResponse.setSuperAdminStaff(superAdminStaff);
	    getDashBoardResponse.setTotalCases(totalCases);
	    getDashBoardResponse.setTotalClients(totalClients);
	    getDashBoardResponse.setTotalEarningThisWeek(totalEarningThisWeek);
	    getDashBoardResponse.setTotalOverallEarning(totalOverallEarning);
	    getDashBoardResponse.setTotalPlans(totalPlans);
	    getDashBoardResponse.setTotalStaff(totalStaff);
	    
	    getDashBoardResponse.setSupremeCourtCases(supremeCourtCases);
	    getDashBoardResponse.setSupremeCourtDivisionalBenchCases(supremeCourtDivisionalBenchCases);
	    getDashBoardResponse.setSupremeCourtFullBenchCases(supremeCourtFullBenchCases);
	    getDashBoardResponse.setSupremeCourtThirdBenchCases(supremeCourtThirdBenchCases);
	    
	    getDashBoardResponse.setHighCourtCases(highCourtCases);
	    
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in DashBoardDetailsServiceImpl --> getDashBoardDetailsService()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}	
		return getDashBoardResponse;
	}

}
