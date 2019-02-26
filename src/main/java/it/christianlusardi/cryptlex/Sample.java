package it.christianlusardi.cryptlex;

import java.time.Instant;

import com.cryptlex.lexactivator.LexActivator;
import com.cryptlex.lexactivator.LexActivatorException;
import com.cryptlex.lexactivator.LicenseCallbackEvent;

public class Sample {

	public static void main(String[] args) {
		int status;
		try {
			// String path = System.getProperty("user.dir") + File.separator +"Product.dat";
			// LexActivator.SetProductFile(path);
			LexActivator.SetProductData("MkMzMjI4MzMxNUMwMkNCNDVFREI5RjU5QTNBQzg4RjA=.Qa62VW/6WatnD8ly5JeqeU9CEHDws42a5rFS7wvnUeOqJg05uFEcLsJiiSfVNRg46sT+fY5pBd+0x3VX6xnI7yyOjsRqprgHlH+BIfP80rBscqFn03mWY4fe/CpElm2V9aPYUyjo31+bvUPyj+QRFWWNNq6qImXWSyMQrnwdIW8BtTLktUMpaGe0/4GLPKhtfjsUeaWeVd8RYkDLDPnHZ9Kbx2vTmzcVD33pLJ9GDwqrcZsi+7EJlbKGCeYF5Dto4HoW3a8k3svnYo2SRMd36BG87zJqHMtVOrSAKLR6FwdSoHFlQ0cRUWLYNttErkRb93c37Lk3nl2aoDF56a/0GHUQXXWGOSJcltNPPJHDjaWxTlPrMbW4GckCFS3rxpL5C1eY1qNEKBIe73DTPo39tdVbk7I9DYDtMTTwnUER0wjBcTQBDlcsVV3vFK5fIgjUa6sUQ5T6K+fAfR4FZDqgA8P7N8nRAmztsgYQkjinNYuBdp6ZPDDrEvnaLEcjaI9poHU21gap7aO8vAF+Y4lCvWl4BGf4obcK7gKI0J97aq7aqAYJfXyT/eR8FtqLkCSSaZXuYJN24ET8ZPAO7ckjZYO3jo27JtF0LMeGnI6STNHSXfGzEo2hs/ZwWEM5bBQrNbt3VrSWB0fFZs2JifJ6DxibvBBF8twv9mqg9Oi5qd+xXi+Y6bk78KbQ6t0ennTCPz+2Cs3ntyfbfQR0UFZ1AMqke5dVgCIHmW7Vol8FMMg=");
			LexActivator.SetProductId("58f9c1f2-c884-488f-9d55-c5628eb8ae96", LexActivator.LA_USER);
			LexActivator.SetLicenseKey("EFC023-238DE4-4DFAA7-F52828-A3D185-932967");
			// Setting license callback is recommended for floating licenses
			// CallbackEventListener eventListener = new CallbackEventListener();
			// LexActivator.SetLicenseCallbackListener(eventListener);
				
			System.out.println(LexActivator.ActivateLicense());
			status = LexActivator.IsLicenseGenuine();
			
			System.out.println("STATUS: "+status);
			
			if (LexActivator.LA_OK == status) {
				System.out.println("License is genuinely activated!");
			} else if (LexActivator.LA_EXPIRED == status) {
				System.out.println("License is genuinely activated but has expired!");
			} else if (LexActivator.LA_GRACE_PERIOD_OVER == status) {
				System.out.println("License is genuinely activated but grace period is over!");
			} else if (LexActivator.LA_SUSPENDED == status) {
				System.out.println("License is genuinely activated but has been suspended!");
			} else {
				int trialStatus;
				trialStatus = LexActivator.IsTrialGenuine();
				if (LexActivator.LA_OK == trialStatus) {
					int trialExpiryDate = LexActivator.GetTrialExpiryDate();
					long daysLeft = (trialExpiryDate - Instant.now().getEpochSecond()) / 86500;
					System.out.println("Trial days left: " + daysLeft);
				} else if (LexActivator.LA_TRIAL_EXPIRED == trialStatus) {
					System.out.println("Trial has expired!");
					// Time to buy the product key and activate the app
					LexActivator.SetLicenseKey("PASTE_LICENSE_KEY");
					LexActivator.SetActivationMetadata("key1", "value1");
					// Activating the product
					status = LexActivator.ActivateLicense(); // Ideally on a button click inside a dialog
					if (LexActivator.LA_OK == status || LexActivator.LA_EXPIRED == status
							|| LexActivator.LA_SUSPENDED == status) {
						System.out.println("License activated successfully: " + status);
					} else {
						System.out.println("License activation failed: " + status);
					}
				} else {
					System.out.println("Either trial has not started or has been tampered!");
					// Activating the trial
					trialStatus = LexActivator.ActivateTrial(); // Ideally on a button click inside a dialog
					if (LexActivator.LA_OK == trialStatus) {
						int trialExpiryDate = LexActivator.GetTrialExpiryDate();
						long daysLeft = (trialExpiryDate - Instant.now().getEpochSecond()) / 86500;
						System.out.println("Trial days left: " + daysLeft);
					} else {
						// Trial was tampered or has expired
						System.out.println("Trial activation failed: " + trialStatus);
					}
				}
			}

		} catch (LexActivatorException ex) {
			System.out.println(ex.getCode() + ": " + ex.getMessage());
		}
	}

}

class CallbackEventListener implements LicenseCallbackEvent {

	// License callback is invoked when IsLicenseGenuine() completes a server sync
	public void LicenseCallback(int status) {
		switch (status) {
		case LexActivator.LA_SUSPENDED:
			System.out.println("The license has been suspended.");
			break;
		case LexActivatorException.LA_E_INET:
			System.out.println("Network connection failure.");
			break;
		default:
			System.out.println("License status: " + status);
			break;
		}
	}
}