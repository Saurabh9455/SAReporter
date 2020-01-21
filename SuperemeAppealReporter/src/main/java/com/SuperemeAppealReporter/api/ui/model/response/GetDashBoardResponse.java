package com.SuperemeAppealReporter.api.ui.model.response;

import java.math.BigDecimal;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetDashBoardResponse {

	private int totalOrders;
	private int pendingOrders;
	private int failedOrders;
	private int completedOrders;
	
	private int totalClients;
	private int activeClients;
	private int inActiveClients;
	
	private int totalStaff;
	private int adminStaff;
	private int dataEntryOperatorStaff;
	private int superAdminStaff;
	
	private int totalCases;
    private int overRuledCases;
    private int liveCases;
    
    private int totalPlans;
    
    
    private Double totalEarningThisWeek;
    private Double totalOverallEarning;
    
    private int supremeCourtCases;
    private int supremeCourtDivisionalBenchCases;
    private int supremeCourtFullBenchCases;
    private int supremeCourtThirdBenchCases;
    
    private int highCourtCases;
}
