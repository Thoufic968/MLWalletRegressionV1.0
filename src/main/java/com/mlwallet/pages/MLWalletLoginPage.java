package com.mlwallet.pages;

import org.openqa.selenium.By;

public class MLWalletLoginPage {
	
	public static By objMobileNumberTextField=By.xpath("//*[android.view.ViewGroup]/descendant::android.widget.EditText");
	public static By objLoginBtn=By.xpath("//*[@text='Login']");
	public static By objOtpTextField=By.xpath("//*[android.view.ViewGroup]/descendant::android.widget.EditText");
	
	public static By objAvailableBalance = By.xpath("//*[@text='Available Balance']");

	public static By objInvalidMobNumberTxt = By.xpath("//*[@text='Mobile number is invalid']");

	public static By objOneTimePin = By.xpath("(//*[@text='One Time Pin'])[1]");

}
