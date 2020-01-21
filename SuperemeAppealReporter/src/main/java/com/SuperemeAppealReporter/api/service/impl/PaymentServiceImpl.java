package com.SuperemeAppealReporter.api.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SuperemeAppealReporter.api.bo.ConfirmPaymentBo;
import com.SuperemeAppealReporter.api.bo.InitiatePaymentBo;
import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.enums.PaymentMode;
import com.SuperemeAppealReporter.api.enums.PaymentStatus;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.io.entity.PaymentEntity;
import com.SuperemeAppealReporter.api.io.entity.SubscriptionPlanEntity;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.io.entity.UserSubscriptionDetailEntity;
import com.SuperemeAppealReporter.api.io.repository.PaymentRepository;
import com.SuperemeAppealReporter.api.io.repository.SubscriptionPlanRepository;
import com.SuperemeAppealReporter.api.io.repository.UserRepository;
import com.SuperemeAppealReporter.api.io.repository.UserSubscriptionDetailRepository;
import com.SuperemeAppealReporter.api.service.PaymentService;
import com.SuperemeAppealReporter.api.ui.model.response.GetPaymentStatusResponse;
import com.SuperemeAppealReporter.api.ui.model.response.InitiatePaymentResponse;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import io.jsonwebtoken.lang.Collections;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Value("${razorpay.key-id}")
	private String key_id;
	
	@Value("${razorpay.key-secret}")
	private String key_secret;
	
	@Autowired
	private SubscriptionPlanRepository subscriptionPlanRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserSubscriptionDetailRepository userSubscriptionDetailRepository;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public InitiatePaymentResponse initiatePaymentService(InitiatePaymentBo initiatePaymentBo) {
		   
		   
		InitiatePaymentResponse initiatePaymentResponse = null;
		
		try {
		
			int planId = initiatePaymentBo.getPlanId();
			SubscriptionPlanEntity subscriptionPlanEntity = subscriptionPlanRepository.findById(planId).orElseThrow(()-> new AppException(ErrorConstant.InvalidPlanIdError.ERROR_TYPE,
					ErrorConstant.InvalidPlanIdError.ERROR_TYPE,ErrorConstant.InvalidPlanIdError.ERROR_MESSAGE));
			
		    	double paymentAmount = subscriptionPlanEntity.getSubscriptionCost()*100;
	    	
			  RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
			  JSONObject orderRequest = new JSONObject();
			  orderRequest.put("amount", paymentAmount); // amount in the smallest currency unit
			  orderRequest.put("currency", "INR");
			  orderRequest.put("receipt", "order_rcptid_111111");
			  orderRequest.put("payment_capture", false);

			  Order order = razorpayClient.Orders.create(orderRequest);
			 /* Order order1 =  razorpayClient.Orders.fetch("order_Dd8PF2mpXjifVk");
			  System.out.println("--Previous Order ---"+order1);*/
			 
			  
			 /**Creating PaymentEntity**/
			  PaymentEntity paymentEntity = new PaymentEntity();
			  paymentEntity.setAmount(paymentAmount);
			  paymentEntity.setPaymentMode(PaymentMode.PAYMENT_GATEYWAY);
			  paymentEntity.setPaymentStatus(PaymentStatus.NOT_STARTED);
			  paymentEntity.setTransaction_id(order.get("id"));
			  paymentRepository.save(paymentEntity);
			  
	
			  
			  initiatePaymentResponse = new InitiatePaymentResponse();
			  initiatePaymentResponse.setData_amount(String.valueOf(paymentAmount));
			  initiatePaymentResponse.setData_currency("INR");
			  initiatePaymentResponse.setData_description(subscriptionPlanEntity.getSubscriptionDescription());
			  initiatePaymentResponse.setData_key(key_id);
			  initiatePaymentResponse.setData_name(subscriptionPlanEntity.getSubscriptionName());
			  initiatePaymentResponse.setData_order_Id(order.get("id"));
			 
			  /**Change below hard coded details to logged in user details**/
			  String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
				
				 UserEntity userEntity = userRepository.getUserEntityByEmail(userEmail);
				 
			  initiatePaymentResponse.setData_prefil_email(userEntity.getEmail());
			  initiatePaymentResponse.setData_prefill_contact(userEntity.getMobile());
			  initiatePaymentResponse.setData_prefill_name(userEntity.getName());
			  return initiatePaymentResponse;
			  
			  
			} 
		
		catch(AppException ex)
		{
			throw ex;
		}
		catch (RazorpayException e) {

			throw new AppException(ErrorConstant.RazorPayError.ERROR_TYPE, ErrorConstant.RazorPayError.ERROR_CODE,
					e.getMessage());
		} catch (JSONException e) {
			throw new AppException(ErrorConstant.JSonExceptionError.ERROR_TYPE,
					ErrorConstant.JSonExceptionError.ERROR_CODE, e.getMessage());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String errorMessage = "Error in PaymentServiceImpl --> initiatePaymentService()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
		}
		
	
		   
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public GetPaymentStatusResponse getPaymentStatusAndCapturePayment(ConfirmPaymentBo confirmPaymentBo) {
	
		GetPaymentStatusResponse getPaymentStatusResponse = null;
		try
		{
		int planId = confirmPaymentBo.getPlanId();
		String orderId = confirmPaymentBo.getOrderId();
		double amount = confirmPaymentBo.getAmount();
		
		
		 SubscriptionPlanEntity subscriptionPlanEntity1 = subscriptionPlanRepository.findById(planId).orElse(null);
		
		 if(amount!=(subscriptionPlanEntity1.getSubscriptionCost()*100))
		 {
			 throw new AppException(ErrorConstant.PaymentConfirmationError.ERROR_TYPE,ErrorConstant.PaymentConfirmationError.ERROR_CODE,ErrorConstant.PaymentConfirmationError.AMOUNT_PLAN_MISMATCH_ERROR_MESSAGE);
		 }
		
		/**Fetching order details**/
		RazorpayClient razorpay = new RazorpayClient(key_id, key_secret);
		List<Payment> paymentListForThisOder = razorpay.Orders.fetchPayments(orderId);
		
		String paymentId = "";
		for(Payment payment : paymentListForThisOder)
		{
			 String paymentStatus = (String)payment.get("status");
			 if(paymentStatus.equalsIgnoreCase("authorized"))
			 {
				 paymentId = (String)payment.get("id");
				 System.out.println("-------------------YES AUTHORIZED-----------");
				 break;
			 }
		}
		
		if(paymentId=="")
		{
			System.out.println("--------NOT AUTHORIZED--------");
			throw new AppException(ErrorConstant.PaymentConfirmationError.ERROR_TYPE,
					ErrorConstant.PaymentConfirmationError.ERROR_CODE,
					ErrorConstant.PaymentConfirmationError.ERROR_MESSAGE+orderId);
		}
		
		/**Capturing the payment**/
		  JSONObject captureRequest = new JSONObject();
		  captureRequest.put("amount", amount); // Amount should be in paise
		  Payment payment = razorpay.Payments.capture(paymentId, captureRequest);
		  String paymentMethod = payment.get("method");
	
		  System.out.println("---------------PAYMENT  CAPTURED SUCCESSFULLY------------");
	
		  /**Fetching userEntity**/
		 String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		
		 UserEntity userEntity = userRepository.getUserEntityByEmail(userEmail);
		 
		 /**Fetching Plan entity**/
		 SubscriptionPlanEntity subscriptionPlanEntity = subscriptionPlanRepository.findById(planId).orElse(null);
		
		 /**calculating Plan End Date**/
		 String planType = subscriptionPlanEntity.getSubscriptionType().toString();
		 
		 int dayCount = 0;
		 //DAILY,MONTHLY,WEEKLY,YEARLY;
		 switch(planType)
		 {
		 case "DAILY" : dayCount = 1;
		 break;
		 case "MONTHLY" : dayCount = 30;
		 break;
		 case "WEEKLY" : dayCount = 7;
		 break;
		 case "YEARLY" : dayCount = 365;
		 break;
		 }
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(new Date());
		 cal.add(Calendar.DAY_OF_MONTH, dayCount);
		 
		 Date endDate = cal.getTime();
		 
		 /**Fetching Payment Entity**/
		 PaymentEntity paymentEntity = paymentRepository.findByTransaction_id(orderId);
		 paymentEntity.setPayment_id(paymentId);
	
	
		  /**Creating an entry in user subscription**/
		   UserSubscriptionDetailEntity userSubscriptionDetailEntity = new UserSubscriptionDetailEntity();
		   userSubscriptionDetailEntity.setIs_plan_active(true);
		   userSubscriptionDetailEntity.setStartDate(new Date());
		   userSubscriptionDetailEntity.setEndDate(endDate);
		   userSubscriptionDetailEntity.setPaymentEntity(paymentEntity);
		   userSubscriptionDetailEntity.setSubscriptionPlanEntity(subscriptionPlanEntity);
		   userSubscriptionDetailEntity.setUserEntity(userEntity);
		   
		
	   	 
	   /**Checking if the user has already an active Plan**/
		List<UserSubscriptionDetailEntity> previousUserSubscriptionDetailEntityList =  userSubscriptionDetailRepository.findByUserId(userEntity.getId(),true);
		
		if(previousUserSubscriptionDetailEntityList!=null && previousUserSubscriptionDetailEntityList.size()>0)
		{
			List<Date> endDateList = new ArrayList<Date>();
		for(UserSubscriptionDetailEntity userSub : previousUserSubscriptionDetailEntityList)
		{
			
			endDateList.add(userSub.getEndDate());
			
		}
		java.util.Collections.sort(endDateList);
		endDate = endDateList.get(endDateList.size()-1);
		
		Date newPlanStartDate = endDate;
		
		Calendar cal11 = Calendar.getInstance();
		 cal11.setTime(newPlanStartDate);
		 cal11.add(Calendar.DAY_OF_MONTH, 1);
		 newPlanStartDate = cal11.getTime();
		
		Calendar cal1 = Calendar.getInstance();
		 cal1.setTime(newPlanStartDate);
		 cal1.add(Calendar.DAY_OF_MONTH, dayCount);
		  userSubscriptionDetailEntity.setStartDate(newPlanStartDate);
		  newPlanStartDate = cal1.getTime();
		   userSubscriptionDetailEntity.setEndDate(newPlanStartDate);

		   userSubscriptionDetailEntity.setIs_plan_active(false);
		}
		 
	 
	   /**saving subscriptionEntity**/
	   userSubscriptionDetailRepository.save(userSubscriptionDetailEntity);
	   
	   paymentEntity.setPaymentStatus(PaymentStatus.SUCCESS);
	   if(paymentMethod.equalsIgnoreCase("card"))
	   {
		   paymentEntity.setPaymentMode(PaymentMode.CARD);
	   }
	   else if(paymentMethod.equalsIgnoreCase("upi"))
	   {
		   paymentEntity.setPaymentMode(PaymentMode.UPI);
	   }
	   else if(paymentMethod.equalsIgnoreCase("emi"))
	   {
		   
		   paymentEntity.setPaymentMode(PaymentMode.EMI);
	   }
	 
	   userEntity.setSubscriptionActive(true);
	   
	  /**creating Payment Confirmation Response **/
	   getPaymentStatusResponse = new GetPaymentStatusResponse();
	   getPaymentStatusResponse.setPaymentDescription("The Payment is received succesfully");
	   getPaymentStatusResponse.setPaymentIdForRazorPay(paymentId);
	   getPaymentStatusResponse.setPaymentStatus(PaymentStatus.SUCCESS.toString());
	   getPaymentStatusResponse.setPlanActive(userSubscriptionDetailEntity.getIs_plan_active());
	   getPaymentStatusResponse.setPlanStartDate(userSubscriptionDetailEntity.getStartDate());
	   getPaymentStatusResponse.setPlanEndDate(userSubscriptionDetailEntity.getEndDate());
	   getPaymentStatusResponse.setOrderId(orderId);
	   getPaymentStatusResponse.setPaymentDate(paymentEntity.getCreatedDate());
	   getPaymentStatusResponse.setPaymentAmount(amount);
	   
	
	   
		
		return getPaymentStatusResponse;
		}
	 
	
	catch(AppException ex)
	{
		throw ex;
	}
	catch (RazorpayException e) {

		throw new AppException(ErrorConstant.RazorPayError.ERROR_TYPE2, ErrorConstant.RazorPayError.ERROR_CODE,
				e.getMessage());
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		String errorMessage = "Error in PaymentServiceImpl --> getPaymentStatusAndCapturePayment()";
		AppException appException = new AppException("Type : " + ex.getClass()
		+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
				ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
		throw appException;
	}
	
		
	}

}
