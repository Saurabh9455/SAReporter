package com.SuperemeAppealReporter.api.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SuperemeAppealReporter.api.bo.ConfirmPaymentBo;
import com.SuperemeAppealReporter.api.bo.InitiatePaymentBo;
import com.SuperemeAppealReporter.api.constant.RestMappingConstant;
import com.SuperemeAppealReporter.api.converter.PaymentConverter;
import com.SuperemeAppealReporter.api.service.PaymentService;
import com.SuperemeAppealReporter.api.ui.model.request.ConfirmPaymentRequest;
import com.SuperemeAppealReporter.api.ui.model.request.InitiatePaymentRequest;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.GetPaymentStatusResponse;
import com.SuperemeAppealReporter.api.ui.model.response.InitiatePaymentResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;

@RestController
@RequestMapping(path=RestMappingConstant.Payment.PAYMENT_BASE_URI)
public class PaymentController {
	
	
	@Autowired
	PaymentService paymentService;
	
	/*@Autowired
	private SubscriptionPlanRepository subscriptionPlanRepository;*/
	
	/****************************************Initiate Payment Handler Method*****************************************/	
	@PostMapping(RestMappingConstant.Payment.INITIATE_PAYMENT_URI)
	public ResponseEntity<BaseApiResponse> initiatePayment(@RequestBody InitiatePaymentRequest initiatePaymentRequest)
	{
		
		/**Converting request into bo**/
		InitiatePaymentBo initiatePaymentBo = PaymentConverter.convertInititatePaymentRequestToIntitiatePaymentBo(initiatePaymentRequest);
		InitiatePaymentResponse initiatePaymentResponse = paymentService.initiatePaymentService(initiatePaymentBo);
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(initiatePaymentResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
		/*	String key_id = "rzp_test_U2csKZt8nvtF2Y";
			String key_secret = "WNApo7ojh7InQWGa7azD5Y9O";
			
			int planId = initiatePaymentRequest.getPlanId();
			SubscriptionPlanEntity subscriptionPlanEntity = subscriptionPlanRepository.findById(planId).orElse(null);
			
			double paymentAmount = subscriptionPlanEntity.getSubscriptionCost();
	
			  RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
			  JSONObject orderRequest = new JSONObject();
			  orderRequest.put("amount", paymentAmount); // amount in the smallest currency unit
			  orderRequest.put("currency", "INR");
			  orderRequest.put("receipt", "order_rcptid_111111");
			  orderRequest.put("payment_capture", false);

			  Order order = razorpayClient.Orders.create(orderRequest);
			  Order order1 =  razorpayClient.Orders.fetch("order_Dd8PF2mpXjifVk");
			  System.out.println("--Previous Order ---"+order1);
			
			  InitiatePaymentResponse initiatePaymentResponse = new InitiatePaymentResponse();
			  initiatePaymentResponse.setData_amount(String.valueOf(paymentAmount*100));
			  initiatePaymentResponse.setData_currency("INR");
			  initiatePaymentResponse.setData_description("This is a very good Plan");
			  initiatePaymentResponse.setData_key(key_id);
			  initiatePaymentResponse.setData_name(subscriptionPlanEntity.getSubscriptionName());
			  initiatePaymentResponse.setData_order_Id(order.get("id"));
			  initiatePaymentResponse.setData_prefil_email("manishchoudhary7496@gmail.com");
			  initiatePaymentResponse.setData_prefill_contact("7889011453");
			  initiatePaymentResponse.setData_prefill_name("Manish Choudhary");
			  return initiatePaymentResponse;*/
			  
			  
		
	}
	
	/****************************************Confirm Payment Handler Method*****************************************/	
	@PostMapping(RestMappingConstant.Payment.CONFIRM_PAYMENT_URI)
	public ResponseEntity<BaseApiResponse> confirmPayment(@RequestBody ConfirmPaymentRequest confirmPaymentRequest)
	{
		
		/**Converting request into bo**/
		ConfirmPaymentBo confirmPaymentBo = PaymentConverter.convertConfirmPaymentRequestToConfirmPaymentBo(confirmPaymentRequest);
		
		/**calling service layer**/
		GetPaymentStatusResponse getPaymentStatusResponse = paymentService.getPaymentStatusAndCapturePayment(confirmPaymentBo);
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(getPaymentStatusResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	
	}
	

}
