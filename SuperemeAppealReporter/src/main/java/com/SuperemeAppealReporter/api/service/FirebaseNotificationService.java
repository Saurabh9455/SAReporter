package com.SuperemeAppealReporter.api.service;

import com.SuperemeAppealReporter.api.ui.model.request.RegisterDeviceRequest;
import com.SuperemeAppealReporter.api.ui.model.request.SendNotificationRequest;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;

public interface FirebaseNotificationService {

	public CommonMessageResponse registerDevice(RegisterDeviceRequest registerDeviceRequest,String email);
	public CommonMessageResponse send(SendNotificationRequest sendRequest);

}
