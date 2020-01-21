package com.SuperemeAppealReporter.api.converter;

import org.springframework.beans.BeanUtils;

import com.SuperemeAppealReporter.api.bo.ConfirmPaymentBo;
import com.SuperemeAppealReporter.api.bo.InitiatePaymentBo;
import com.SuperemeAppealReporter.api.ui.model.request.ConfirmPaymentRequest;
import com.SuperemeAppealReporter.api.ui.model.request.InitiatePaymentRequest;

public class PaymentConverter {

	public static InitiatePaymentBo convertInititatePaymentRequestToIntitiatePaymentBo(InitiatePaymentRequest initiatePaymentRequest)
	{
		InitiatePaymentBo initiatePaymentBo = new InitiatePaymentBo();
		BeanUtils.copyProperties(initiatePaymentRequest, initiatePaymentBo);
		return initiatePaymentBo;
	}
	
	public static ConfirmPaymentBo convertConfirmPaymentRequestToConfirmPaymentBo(ConfirmPaymentRequest confirmPaymentRequest)
	{
		ConfirmPaymentBo confirmPaymentBo = new ConfirmPaymentBo();
		BeanUtils.copyProperties(confirmPaymentRequest, confirmPaymentBo);
		return confirmPaymentBo;
	}
}
