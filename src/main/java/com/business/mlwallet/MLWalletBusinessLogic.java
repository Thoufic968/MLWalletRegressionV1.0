package com.business.mlwallet;


import com.driverInstance.CommandBase;
import com.driverInstance.DriverManager;
import com.mlwallet.pages.*;
import com.propertyfilereader.PropertyFileReader;
import com.utility.ExtentReporter;
import com.utility.LoggingUtils;
import com.utility.Utilities;
import io.appium.java_client.android.GsmVoiceState;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;


public class MLWalletBusinessLogic{
	
	static LoggingUtils logger = new LoggingUtils();
	private int timeout;
	private int retryCount;

	public static SoftAssert softAssert = new SoftAssert();

	public static PropertyFileReader prop = new PropertyFileReader(".\\properties\\testdata.properties");

	public MLWalletBusinessLogic(String Application,String deviceName, String portno) throws InterruptedException {
		new CommandBase(Application, deviceName,  portno);
		init();
	}
	
	public void init() {
		PropertyFileReader handler = new PropertyFileReader("properties/Execution.properties");
		setTimeout(Integer.parseInt(handler.getproperty("TIMEOUT")));
		setRetryCount(Integer.parseInt(handler.getproperty("RETRY_COUNT")));
		logger.info("Loaded the following properties" + " TimeOut :" + getTimeout() + " RetryCount :" + getRetryCount());
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	public void tearDown() {
		softAssert.assertAll();
		logger.info("Session ID: "+((RemoteWebDriver) DriverManager.getAppiumDriver()).getSessionId());
		ExtentReporter.extentLogger("","Session ID: "+((RemoteWebDriver) DriverManager.getAppiumDriver()).getSessionId());
		logger.info("Session is quit");
		ExtentReporter.extentLogger("","Session is quit");

		Utilities.setScreenshotSource();
		DriverManager.getAppiumDriver().quit();
	}
	
//================================ LOG IN==============================================//
	public void mlWalletLogin(String sTier) throws Exception
	{
	   Utilities.explicitWaitVisible(MLWalletLoginPage.objMobileNumberTextField,10);
       Utilities.click(MLWalletLoginPage.objMobileNumberTextField, "Mobile Number Text Field");
       Utilities.type(MLWalletLoginPage.objMobileNumberTextField, sTier, "Mobile Number Text Field");
       Utilities.click(MLWalletLoginPage.objLoginBtn, "Login Button");
	   enterOTP(prop.getproperty("Valid_OTP"));
	   Utilities.explicitWaitVisible(MLWalletLoginPage.objAvailableBalance,10);
	   if(Utilities.verifyElementPresent(MLWalletLoginPage.objAvailableBalance,Utilities.getTextVal(MLWalletLoginPage.objAvailableBalance,"Text"))){
		   logger.info("Application Logged In Successfully");
	   }else{
		   logger.info("Application not get Logged In Successfully");
	   }
	}
//===================================LOG OUT=============================================================//
	public void mlWalletLogout() throws Exception {
		if(Utilities.verifyElementPresent(MLWalletLogOutPage.objHamburgerMenu,"Hamburger Menu")) {
			Utilities.click(MLWalletLogOutPage.objHamburgerMenu, "Hamburger Menu");
			Utilities.click(MLWalletLogOutPage.objLogoutBtn, Utilities.getTextVal(MLWalletLogOutPage.objLogoutBtn, "Button"));
			Thread.sleep(2000);
			Utilities.click(MLWalletLogOutPage.objLogoutBtn, Utilities.getTextVal(MLWalletLogOutPage.objLogoutBtn, "Button"));
			Utilities.click(MLWalletLogOutPage.objChangeNumber, Utilities.getTextVal(MLWalletLogOutPage.objChangeNumber, "Link"));
		}
		if(Utilities.verifyElementPresent(MLWalletLoginPage.objLoginBtn,Utilities.getTextVal(MLWalletLoginPage.objLoginBtn,"Link"))) {
			logger.info("Application Logged Out Successfully");
		}else{
			logger.info("Application not get Logged Out Successfully");
		}

	}
//================================== Enter OTP ===================================================//

	public void enterOTP(String OTP) throws Exception {
		Utilities.explicitWaitVisible(MLWalletLoginPage.objOneTimePin,5);
		Utilities.verifyElementPresent(MLWalletLoginPage.objOneTimePin,Utilities.getTextVal(MLWalletLoginPage.objOneTimePin,"Page"));
		Utilities.verifyElementPresent(MLWalletLoginPage.objOtpTextField,"OTP text Field");
		Thread.sleep(3000);
		Utilities.type(MLWalletLoginPage.objOtpTextField, OTP, "OTP Text Field");
	}
//========================================= LOGIN SCENARIOS======================================//

	public void LogInScenarioWithValidMobNumber_Lgn_TC_01() throws Exception {
		ExtentReporter.HeaderChildNode("Login Scenarios With Valid Mobile Number");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		if(Utilities.verifyElementPresent(MLWalletLoginPage.objAvailableBalance,Utilities.getTextVal(MLWalletLoginPage.objAvailableBalance,"Text"))){
			logger.info("Lgn_TC_01, Logged In Successfully and redirected to Dashboard");
			ExtentReporter.extentLoggerPass("Lgn_TC_01", "Lgn_TC_01, Logged In Successfully and redirected to Dashboard");
			Utilities.setScreenshotSource();
			System.out.println("-----------------------------------------------------------");

		}
		mlWalletLogout();
	}

	public void LogInScenarioWithInvalidMobNumber() throws Exception {
		ExtentReporter.HeaderChildNode("Login Scenarios With Invalid Mobile Number");
		Utilities.explicitWaitVisibility(MLWalletLoginPage.objMobileNumberTextField,10);
		Utilities.click(MLWalletLoginPage.objMobileNumberTextField, "Mobile Number Text Field");
		Utilities.type(MLWalletLoginPage.objMobileNumberTextField, prop.getproperty("Invalid_MobileNumber"), "Mobile Number Text Field");
		Utilities.click(MLWalletLoginPage.objLoginBtn, "Login Button");
		if(Utilities.verifyElementPresent(MLWalletLoginPage.objInvalidMobNumberTxt,Utilities.getTextVal(MLWalletLoginPage.objInvalidMobNumberTxt,"Error Message"))){
			logger.info("Lgn_TC_02, Mobile number is Invalid Error Message is Displayed");
			ExtentReporter.extentLoggerPass("Lgn_TC_02", "Lgn_TC_02, Mobile number is Invalid Error Message is Displayed");
			Utilities.setScreenshotSource();
			System.out.println("-----------------------------------------------------------");
		}
	}

	public void LogInScenarioWithValidOTP() throws Exception {
		ExtentReporter.HeaderChildNode("Login Scenarios With Valid OTP");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		if(Utilities.verifyElementPresent(MLWalletLoginPage.objAvailableBalance,Utilities.getTextVal(MLWalletLoginPage.objAvailableBalance,"Text"))){
			logger.info("Lgn_TC_03, Logged In Successfully and redirected to Dashboard");
			ExtentReporter.extentLoggerPass("Lgn_TC_03", "Lgn_TC_03, Logged In Successfully and redirected to Dashboard");
			Utilities.setScreenshotSource();
			System.out.println("-----------------------------------------------------------");
		}
		mlWalletLogout();
	}

	public void LogInScenarioWithInValidOTP() throws Exception {
		ExtentReporter.HeaderChildNode("Login Scenarios With InValid OTP");
		Utilities.explicitWaitVisibility(MLWalletLoginPage.objMobileNumberTextField,10);
		Utilities.click(MLWalletLoginPage.objMobileNumberTextField, "Mobile Number Text Field");
		Utilities.type(MLWalletLoginPage.objMobileNumberTextField, prop.getproperty("Branch_Verified"), "Mobile Number Text Field");
		Utilities.click(MLWalletLoginPage.objLoginBtn, "Login Button");
		Utilities.type(MLWalletLoginPage.objOtpTextField, prop.getproperty("InValid_OTP"), "OTP Text Field");
	}


//========================================CASH IN VIA BRANCH===============================================//

	public void cashOutWithdrawBank(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"))) {
			Utilities.click(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"));
			Utilities.click(MLWalletCashOutPage.BogusBank,Utilities.getTextVal(MLWalletCashOutPage.BogusBank,"Bank"));
			if(Utilities.verifyElementPresent(MLWalletCashOutPage.objBankInformation, Utilities.getTextVal(MLWalletCashOutPage.objBankInformation, "Button"))) {
				Utilities.type(MLWalletCashOutPage.objAccountNumberField,"5555555555","Account Number Field");
				Utilities.type(MLWalletCashOutPage.objFirstname,prop.getproperty("First_Name"),"Account Holder First Name");
				Utilities.type(MLWalletCashOutPage.objMiddleName,prop.getproperty("Middle_Name"),"Account Holder Middle Name");
				Utilities.click(MLWalletCashOutPage.objCheckBox,"Check Box");
				Utilities.type(MLWalletCashOutPage.objLastName,prop.getproperty("Last_Name"),"Account Holder Last Name");
				Utilities.Swipe("UP",1);
				Utilities.type(MLWalletCashOutPage.objEmailAddress,prop.getproperty("Email"),"Account Holder Email Address");
				Utilities.click(MLWalletCashOutPage.objConfirmBtn,Utilities.getTextVal(MLWalletCashOutPage.objConfirmBtn,"Button"));
				Thread.sleep(3000);
				Utilities.type(MLWalletCashOutPage.objAmountField, Amount, "Amount to Send");
				Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
				Utilities.click(MLWalletCashOutPage.objContinueBtn, Utilities.getTextVal(MLWalletCashOutPage.objContinueBtn, "Button"));
				Utilities.Swipe("UP",2);
				Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
				Utilities.click(MLWalletCashOutPage.objLocationPermission, Utilities.getTextVal(MLWalletCashOutPage.objLocationPermission, "Button"));
				enterOTP(prop.getproperty("Valid_OTP"));
				if(Utilities.verifyElementPresent(MLWalletCashOutPage.objTransactionReceipt,Utilities.getTextVal(MLWalletCashOutPage.objTransactionReceipt,"Text"))){
					Utilities.verifyElementPresent(MLWalletCashOutPage.objTransactionSuccessMessage,Utilities.getTextVal(MLWalletCashOutPage.objTransactionSuccessMessage,"Message"));
					String sTransactionSuccess = Utilities.getText(MLWalletCashOutPage.objTransactionSuccessMessage);
					Utilities.assertionValidation(sTransactionSuccess,"Transaction Successful");
					Utilities.verifyElementPresent(MLWalletCashOutPage.objTransactionNo,Utilities.getTextVal(MLWalletCashOutPage.objTransactionNo,"Transaction Number"));
					String sTransactionNumber = Utilities.getText(MLWalletCashOutPage.objTransactionNo);
					System.out.println(sTransactionNumber);
					Utilities.Swipe("UP",2);
					Utilities.click(MLWalletCashOutPage.objBackToHomeBtn,Utilities.getTextVal(MLWalletCashOutPage.objBackToHomeBtn,"Button"));
					Thread.sleep(3000);
					Utilities.Swipe("DOWN",2);
					Utilities.Swipe("UP", 1);
					Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
					Utilities.click(MLWalletHomePage.objCashOutButton, Utilities.getTextVal(MLWalletHomePage.objCashOutButton, "Text"));
					if (Utilities.verifyElementPresent(MLWalletCashOutPage.objTransactionDetails, Utilities.getTextVal(MLWalletCashOutPage.objTransactionDetails, "Page"))) {
						String sReferenceNumberInCashOut = Utilities.getText(MLWalletCashOutPage.objReferenceNumberInCashOut);
						System.out.println(sReferenceNumberInCashOut);
						Utilities.assertionValidation(sReferenceNumberInCashOut, sTransactionNumber);
						logger.info("WM_TC_01, Successfully Withdraw Money to Bank");
						ExtentReporter.extentLoggerPass("WM_TC_01", "WM_TC_01, Successfully Withdraw Money to Bank");
						Utilities.setScreenshotSource();
						System.out.println("-----------------------------------------------------------");
					}
				}
				backArrowBtn(1);
			}
			mlWalletLogout();
		}
	}

	public void cashOutWithInvalidAccNumber() throws Exception {
		ExtentReporter.HeaderChildNode("cashOut With Invalid Account Number");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"))) {
			Utilities.click(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"));
			Utilities.click(MLWalletCashOutPage.BogusBank, Utilities.getTextVal(MLWalletCashOutPage.BogusBank, "Bank"));
			if (Utilities.verifyElementPresent(MLWalletCashOutPage.objBankInformation, Utilities.getTextVal(MLWalletCashOutPage.objBankInformation, "Button"))) {
				Utilities.type(MLWalletCashOutPage.objAccountNumberField, "55555", "Account Number Field");
				Utilities.type(MLWalletCashOutPage.objFirstname, prop.getproperty("First_Name"), "Account Holder First Name");
				Utilities.type(MLWalletCashOutPage.objMiddleName, prop.getproperty("Middle_Name"), "Account Holder Middle Name");
				Utilities.click(MLWalletCashOutPage.objCheckBox, "Check Box");
				Utilities.type(MLWalletCashOutPage.objLastName, prop.getproperty("Last_Name"), "Account Holder Last Name");
				Utilities.Swipe("UP", 1);
				Utilities.type(MLWalletCashOutPage.objEmailAddress, prop.getproperty("Email"), "Account Holder Email Address");
				Utilities.click(MLWalletCashOutPage.objConfirmBtn, Utilities.getTextVal(MLWalletCashOutPage.objConfirmBtn, "Button"));
				Thread.sleep(5000);
				if(Utilities.verifyElementPresent(MLWalletCashOutPage.objAccInvalidErrorMsg,Utilities.getTextVal(MLWalletCashOutPage.objAccInvalidErrorMsg,"Text Message"))){
					String sInvalidAccTxt = Utilities.getText(MLWalletCashOutPage.objAccInvalidErrorMsg);
					String ExpectedTxt = "Bank Account provided not valid. Please check the account details and try again.";
					Utilities.assertionValidation(sInvalidAccTxt,ExpectedTxt);
					logger.info("WM_TC_02, Bank Account provided not valid. Error Message is Validated");
					ExtentReporter.extentLoggerPass("WM_TC_02", "WM_TC_02, Bank Account provided not valid. Error Message is Validated");
					Utilities.setScreenshotSource();
					System.out.println("-----------------------------------------------------------");
				}
				Utilities.click(MLWalletCashOutPage.objOkBtn,Utilities.getTextVal(MLWalletCashOutPage.objOkBtn,"Button"));
				Utilities.click(MLWalletCashOutPage.objBackArrow,"Bank InformationBack Arrow Button");
				Utilities.click(MLWalletCashOutPage.objBankListBackArrow,"Bank List Back Arrow Button");
				Utilities.click(MLWalletCashOutPage.objCashOutOptionsBackArrowBtn,"CashOut Page Back Arrow Btn");
			}
		}
		mlWalletLogout();
	}


	public void cashOutWithdrawBankMaxAmount(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch Max Amount");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"))) {
			Utilities.click(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"));
			Utilities.click(MLWalletCashOutPage.BogusBank,Utilities.getTextVal(MLWalletCashOutPage.BogusBank,"Bank"));
			if(Utilities.verifyElementPresent(MLWalletCashOutPage.objBankInformation, Utilities.getTextVal(MLWalletCashOutPage.objBankInformation, "Button"))) {
				Utilities.type(MLWalletCashOutPage.objAccountNumberField,"5555555555","Account Number Field");
				Utilities.type(MLWalletCashOutPage.objFirstname,prop.getproperty("First_Name"),"Account Holder First Name");
				Utilities.type(MLWalletCashOutPage.objMiddleName,prop.getproperty("Middle_Name"),"Account Holder Middle Name");
				Utilities.click(MLWalletCashOutPage.objCheckBox,"Check Box");
				Utilities.type(MLWalletCashOutPage.objLastName,prop.getproperty("Last_Name"),"Account Holder Last Name");
				Utilities.Swipe("UP",1);
				Utilities.type(MLWalletCashOutPage.objEmailAddress,prop.getproperty("Email"),"Account Holder Email Address");
				Utilities.click(MLWalletCashOutPage.objConfirmBtn,Utilities.getTextVal(MLWalletCashOutPage.objConfirmBtn,"Button"));
				Thread.sleep(3000);
				Utilities.type(MLWalletCashOutPage.objAmountField, Amount, "Amount to Send");
				Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
				Thread.sleep(3000);
				String sDragonPopUpMsg = Utilities.getText(MLWalletCashOutPage.objDragonPayPopUpMsg);
				String sExpectedMsg = "Dragon Pay charges a fee of 35.00 pesos for this transaction. Do you wish to continue with your transaction?";
				Utilities.assertionValidation(sDragonPopUpMsg,sExpectedMsg);
				Utilities.click(MLWalletCashOutPage.objContinueBtn, Utilities.getTextVal(MLWalletCashOutPage.objContinueBtn, "Button"));
				Utilities.Swipe("UP",2);
				Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
				Thread.sleep(5000);
				String sErrorMsg = Utilities.getText(MLWalletCashOutPage.objBankMaxLimitTxt);
				String sExpectedErrorMsg = "The maximum Bank Cash-out per transaction set for your verification level is P50,000.00. Please try again.";
				Utilities.assertionValidation(sErrorMsg,sExpectedErrorMsg);
				logger.info("WM_TC_03, The Maximum Bank Cash-out per transaction Msg is Validated");
				ExtentReporter.extentLoggerPass("WM_TC_03", "WM_TC_03, The Maximum Bank Cash-out per transaction Msg is Validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			Utilities.click(MLWalletCashOutPage.objOkBtn,Utilities.getTextVal(MLWalletCashOutPage.objOkBtn,"Button"));
			Utilities.click(MLWalletCashOutPage.objBackArrow,"Bank InformationBack Arrow Button");
			Utilities.click(MLWalletCashOutPage.objBackArrow,"Bank InformationBack Arrow Button");
			Utilities.click(MLWalletCashOutPage.objBackArrow,"Bank InformationBack Arrow Button");
			Utilities.click(MLWalletCashOutPage.objBankListBackArrow,"Bank List Back Arrow Button");
			Utilities.click(MLWalletCashOutPage.objCashOutOptionsBackArrowBtn,"CashOut Page Back Arrow Btn");
		}
		mlWalletLogout();
	}


	public void cashOutWithdrawMinTransactionLimit(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch Max Amount");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(MLWalletCashOutPage.objCashOut, "CashOut / Withdraw Button");
		if (Utilities.verifyElementPresent(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"))) {
			Utilities.click(MLWalletCashOutPage.objToAnyBank, Utilities.getTextVal(MLWalletCashOutPage.objToAnyBank, "Button"));
			Utilities.click(MLWalletCashOutPage.BogusBank,Utilities.getTextVal(MLWalletCashOutPage.BogusBank,"Bank"));
			if(Utilities.verifyElementPresent(MLWalletCashOutPage.objBankInformation, Utilities.getTextVal(MLWalletCashOutPage.objBankInformation, "Button"))) {
				Utilities.type(MLWalletCashOutPage.objAccountNumberField,"5555555555","Account Number Field");
				Utilities.type(MLWalletCashOutPage.objFirstname,prop.getproperty("First_Name"),"Account Holder First Name");
				Utilities.type(MLWalletCashOutPage.objMiddleName,prop.getproperty("Middle_Name"),"Account Holder Middle Name");
				Utilities.click(MLWalletCashOutPage.objCheckBox,"Check Box");
				Utilities.type(MLWalletCashOutPage.objLastName,prop.getproperty("Last_Name"),"Account Holder Last Name");
				Utilities.Swipe("UP",1);
				Utilities.type(MLWalletCashOutPage.objEmailAddress,prop.getproperty("Email"),"Account Holder Email Address");
				Utilities.click(MLWalletCashOutPage.objConfirmBtn,Utilities.getTextVal(MLWalletCashOutPage.objConfirmBtn,"Button"));
				Utilities.type(MLWalletCashOutPage.objAmountField, Amount, "Amount to Send");
				Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
				Thread.sleep(5000);
				String sMinimumTransactionErrorMsg = Utilities.getText(MLWalletCashOutPage.objMinimumTransactionErrorMsg);
				String sExpectedMsg = "The supplied amount is less than the required minimum transaction limit";
				Utilities.assertionValidation(sMinimumTransactionErrorMsg,sExpectedMsg);
				logger.info("WM_TC_04, The supplied amount is less than the required minimum transaction limit Error Msg is validated");
				ExtentReporter.extentLoggerPass("WM_TC_04", "WM_TC_04, The supplied amount is less than the required minimum transaction limit Error Msg is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			Utilities.click(MLWalletCashOutPage.objOkBtn,Utilities.getTextVal(MLWalletCashOutPage.objOkBtn,"Button"));
			Utilities.click(MLWalletCashOutPage.objBackArrow,"Bank InformationBack Arrow Button");
			Utilities.click(MLWalletCashOutPage.objBackArrow,"Bank InformationBack Arrow Button");
			Utilities.click(MLWalletCashOutPage.objBankListBackArrow,"Bank List Back Arrow Button");
			Utilities.click(MLWalletCashOutPage.objCashOutOptionsBackArrowBtn,"CashOut Page Back Arrow Btn");
		}
		mlWalletLogout();
	}





	public void cashOutWithdrawBranch(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(MLWalletCashOutPage.objCashOut,"CashOut / Withdraw Button");
		if(Utilities.verifyElementPresent(MLWalletCashOutPage.objToAnyMLBranch,Utilities.getTextVal(MLWalletCashOutPage.objToAnyMLBranch,"Button"))) {
			Utilities.click(MLWalletCashOutPage.objToAnyMLBranch, Utilities.getTextVal(MLWalletCashOutPage.objToAnyMLBranch, "Button"));
			Utilities.type(MLWalletCashOutPage.objAmountField, Amount, "Amount to Send");
			Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
			Utilities.click(MLWalletCashOutPage.objContinueBtn, Utilities.getTextVal(MLWalletCashOutPage.objContinueBtn, "Button"));
			enterOTP(prop.getproperty("Valid_OTP"));
			if (Utilities.verifyElementPresent(MLWalletCashOutPage.objCashOutToBranch, Utilities.getTextVal(MLWalletCashOutPage.objCashOutToBranch, "Page"))) {
				Utilities.verifyElementPresent(MLWalletCashOutPage.objCreatedDate, Utilities.getTextVal(MLWalletCashOutPage.objCreatedDate, "Date"));
				Utilities.verifyElementPresent(MLWalletCashOutPage.objReferenceNumber, Utilities.getTextVal(MLWalletCashOutPage.objReferenceNumber, "Reference Number"));
				String nReference = Utilities.getText(MLWalletCashOutPage.objReferenceNumber);
				System.out.println(nReference);
				String sReferenceNumber = nReference.substring(5,16);
				System.out.println(sReferenceNumber);
				Utilities.click(MLWalletCashOutPage.objBackToHomeBtn, Utilities.getTextVal(MLWalletCashOutPage.objBackToHomeBtn, "Button"));
				Utilities.Swipe("DOWN", 2);
				Utilities.Swipe("UP", 1);
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.click(MLWalletHomePage.objCashOutButton, Utilities.getTextVal(MLWalletHomePage.objCashOutButton, "Text"));
				if (Utilities.verifyElementPresent(MLWalletCashOutPage.objTransactionDetails, Utilities.getTextVal(MLWalletCashOutPage.objTransactionDetails, "Page"))) {
					String sReferenceNumberInCashOut = Utilities.getText(MLWalletCashOutPage.objReferenceNumberInCashOut);
					System.out.println(sReferenceNumberInCashOut);
					Utilities.assertionValidation(sReferenceNumberInCashOut, sReferenceNumber);
					logger.info("Reference Number is matching with recent Transaction");
					logger.info("WM_TC_05, Successfully Withdraw Money to ML Branch");
					ExtentReporter.extentLoggerPass("WM_TC_05", "WM_TC_05, Successfully Withdraw Money to ML Branch");
					Utilities.setScreenshotSource();
					System.out.println("-----------------------------------------------------------");
				}
			}
			Utilities.click(MLWalletCashOutPage.objBackArrowBtn,"Back Arrow Button");
		}
		mlWalletLogout();
	}

	public void cashOutMaxLimit(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(MLWalletCashOutPage.objCashOut,"CashOut / Withdraw Button");
		if(Utilities.verifyElementPresent(MLWalletCashOutPage.objToAnyMLBranch,Utilities.getTextVal(MLWalletCashOutPage.objToAnyMLBranch,"Button"))) {
			Utilities.click(MLWalletCashOutPage.objToAnyMLBranch, Utilities.getTextVal(MLWalletCashOutPage.objToAnyMLBranch, "Button"));
			Utilities.type(MLWalletCashOutPage.objAmountField, Amount, "Amount to Send");
			Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
			Utilities.click(MLWalletCashOutPage.objContinueBtn, Utilities.getTextVal(MLWalletCashOutPage.objContinueBtn, "Button"));
			Thread.sleep(5000);
			if(Utilities.verifyElementPresent(MLWalletCashOutPage.objMaxLimitTxt,Utilities.getTextVal(MLWalletCashOutPage.objMaxLimitTxt,"Text Message"))){
				String sMaxLimitTxt = Utilities.getText(MLWalletCashOutPage.objMaxLimitTxt);
				String ExpectedTxt = "The maximum Branch Cash-out per day set for your verification level is P40,000.00. Please try again.";
				Utilities.assertionValidation(sMaxLimitTxt,ExpectedTxt);
				logger.info("WM_TC_06, The supplied amount us less than the required minimum transaction limit. Error Message is Validated");
				ExtentReporter.extentLoggerPass("WM_TC_06", "WM_TC_06, The supplied amount us less than the required minimum transaction limit. Error Message is Validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			Utilities.click(MLWalletCashOutPage.objOkBtn,Utilities.getTextVal(MLWalletCashOutPage.objOkBtn,"Button"));
			Utilities.click(MLWalletCashOutPage.objCashOutPageBackArrowBtn,"CashOut Page Back Arrow Button");
			Utilities.click(MLWalletCashOutPage.objCashOutOptionsBackArrowBtn,"CashOut Options Back Arrow Button");
		}
		mlWalletLogout();
	}

	public void cashOutBuyerTierLevelAcc(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Cash Out Withdraw Branch");
		mlWalletLogin(prop.getproperty("Buyer_Tier"));
		Utilities.click(MLWalletCashOutPage.objCashOut,"CashOut / Withdraw Button");
		if(Utilities.verifyElementPresent(MLWalletCashOutPage.objToAnyMLBranch,Utilities.getTextVal(MLWalletCashOutPage.objToAnyMLBranch,"Button"))) {
			Utilities.click(MLWalletCashOutPage.objToAnyMLBranch, Utilities.getTextVal(MLWalletCashOutPage.objToAnyMLBranch, "Button"));
			Utilities.type(MLWalletCashOutPage.objAmountField, Amount, "Amount to Send");
			Utilities.click(MLWalletCashOutPage.objNextBtn, Utilities.getTextVal(MLWalletCashOutPage.objNextBtn, "Button"));
			Utilities.click(MLWalletCashOutPage.objContinueBtn, Utilities.getTextVal(MLWalletCashOutPage.objContinueBtn, "Button"));
			Thread.sleep(5000);
			if(Utilities.verifyElementPresent(MLWalletCashOutPage.objMaxLimitTxt,Utilities.getTextVal(MLWalletCashOutPage.objMaxLimitTxt,"Text Message"))){
				String sErrorMessage = Utilities.getText(MLWalletCashOutPage.objMaxLimitTxt);
				String ExpectedTxt = "Branch Cash-out is not allowed for customers at this verification level. Please upgrade your account to use this service.";
				Utilities.assertionValidation(sErrorMessage,ExpectedTxt);
				logger.info("WM_TC_08, Branch Cash-out is not allowed for customers at this verification level. Error Message is Validated");
				ExtentReporter.extentLoggerPass("WM_TC_08", "WM_TC_08, Branch Cash-out is not allowed for customers at this verification level. Error Message is Validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			Utilities.click(MLWalletCashOutPage.objOkBtn,Utilities.getTextVal(MLWalletCashOutPage.objOkBtn,"Button"));
			Utilities.click(MLWalletCashOutPage.objCashOutPageBackArrowBtn,"CashOut Page Back Arrow Button");
			Utilities.click(MLWalletCashOutPage.objCashOutOptionsBackArrowBtn,"CashOut Options Back Arrow Button");
		}
		mlWalletLogout();
	}

//================================ Send/Transfer To any ML Branch ============================================//

	public void sendMoneyToMLBranch(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn,Utilities.getTextVal(SendTransferPage.objSendTransferBtn,"Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney,Utilities.getTextVal(SendTransferPage.objSendMoney,"Page"));
		if(Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch,Utilities.getTextVal(SendTransferPage.objToAnyMLBranch,"Button"))){
			Utilities.click(SendTransferPage.objToAnyMLBranch,Utilities.getTextVal(SendTransferPage.objToAnyMLBranch,"Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala,5);
		    Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala,Utilities.getTextVal(SendTransferPage.objKwartaPadala,"Page"));
			Utilities.type(SendTransferPage.objFirstname,prop.getproperty("First_Name"),"First Name Text Field");
			Utilities.type(SendTransferPage.objMiddleName,prop.getproperty("Middle_Name"),"Middle Name Text Field");
			Utilities.click(SendTransferPage.objCheckBox,"Check Box");
			Utilities.type(SendTransferPage.objLastName,prop.getproperty("Last_Name"),"Last Name Text Field");
			Utilities.type(SendTransferPage.objMobileNumber,prop.getproperty("Branch_Verified"),"Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala,5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala,Utilities.getTextVal(SendTransferPage.objKwartaPadala,"Page"));
			Utilities.type(SendTransferPage.objAmountTxtField,Amount,"Amount text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			Utilities.verifyElementPresent(SendTransferPage.objSelectPaymentMethod,Utilities.getTextVal(SendTransferPage.objSelectPaymentMethod,"Page"));
			Utilities.click(SendTransferPage.objMLWalletBalance,Utilities.getTextVal(SendTransferPage.objMLWalletBalance,"Button"));
			Utilities.verifyElementPresent(SendTransferPage.objConfirmDetails,Utilities.getTextVal(SendTransferPage.objConfirmDetails,"Page"));
			Utilities.click(SendTransferPage.objConfirmBtn,Utilities.getTextVal(SendTransferPage.objConfirmBtn,"Button"));
			Utilities.click(SendTransferPage.objLocationPermission,Utilities.getTextVal(SendTransferPage.objLocationPermission,"Button"));
			enterOTP(prop.getproperty("Valid_OTP"));
			if(Utilities.verifyElementPresent(SendTransferPage.objSendMoneySuccessful,Utilities.getTextVal(SendTransferPage.objSendMoneySuccessful,"Message"))) {
				Utilities.verifyElementPresent(SendTransferPage.objPHPAmount, Utilities.getTextVal(SendTransferPage.objPHPAmount, "Amount"));
				Utilities.verifyElementPresent(SendTransferPage.objDate, Utilities.getTextVal(SendTransferPage.objDate, "Date"));
				Utilities.verifyElementPresent(SendTransferPage.objReferenceNumber, Utilities.getTextVal(SendTransferPage.objReferenceNumber, "Reference Number"));
				String sReference = Utilities.getText(SendTransferPage.objReferenceNumber);
				System.out.println(sReference);
				String sReferenceNumber = sReference.substring(9,20);
				System.out.println(sReferenceNumber);
				Utilities.Swipe("UP",2);
				Utilities.click(SendTransferPage.objBackToHomeBtn,Utilities.getTextVal(SendTransferPage.objBackToHomeBtn,"Button"));
				Thread.sleep(3000);
				Utilities.Swipe("DOWN",2);
				Utilities.Swipe("UP", 1);
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.click(MLWalletHomePage.objKwartaPadala, Utilities.getTextVal(MLWalletHomePage.objKwartaPadala, "Text"));
				if (Utilities.verifyElementPresent(SendTransferPage.objReferenceNumberInTransactionDetails, Utilities.getTextVal(SendTransferPage.objReferenceNumberInTransactionDetails, "Page"))) {
					String sReferenceNumberInCashOut = Utilities.getText(SendTransferPage.objReferenceNumberInTransactionDetails);
					System.out.println(sReferenceNumberInCashOut);
					Utilities.assertionValidation(sReferenceNumberInCashOut, sReferenceNumber);
					logger.info("STB_TC_01, Successfully sent Amount to ML Branch and Transaction Details is validated");
					ExtentReporter.extentLoggerPass("STB_TC_01", "STB_TC_01, Successfully sent Amount to ML Branch and Transaction Details is validated");
					Utilities.setScreenshotSource();
					System.out.println("-----------------------------------------------------------");
				}
				backArrowBtn(1);
			}
		}
		mlWalletLogout();
	}

	public void sendMoneyRequiredDetails() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Invalid Bank Details");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn,Utilities.getTextVal(SendTransferPage.objSendTransferBtn,"Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney,Utilities.getTextVal(SendTransferPage.objSendMoney,"Page"));
		if(Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch,Utilities.getTextVal(SendTransferPage.objToAnyMLBranch,"Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objFirstNameRequiredMsg,Utilities.getTextVal(SendTransferPage.objFirstNameRequiredMsg,"Error Message"))){
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objFirstNameRequiredMsg);
				String sExpectedMsg = "First name is required";
				Utilities.assertionValidation(sFirstNameErrorMsg,sExpectedMsg);
			}
            Utilities.hideKeyboard();
			Utilities.type(SendTransferPage.objFirstname,prop.getproperty("First_Name"),"First Name Text Field");
			Utilities.type(SendTransferPage.objFirstname,prop.getproperty("First_Name"),"First Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objMiddleNameRequiredMsg,Utilities.getTextVal(SendTransferPage.objMiddleNameRequiredMsg,"Error Message"))){
				String sMiddleNameRequiredMsg = Utilities.getText(SendTransferPage.objMiddleNameRequiredMsg);
				String sExpectedMsg = "Middle name is required";
				Utilities.assertionValidation(sMiddleNameRequiredMsg,sExpectedMsg);
			}
			Thread.sleep(3000);
			Utilities.type(SendTransferPage.objMiddleName,prop.getproperty("Middle_Name"),"Middle Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objLastNameRequiredMsg,Utilities.getTextVal(SendTransferPage.objLastNameRequiredMsg,"Error Message"))){
				String sLastNameRequiredMsg = Utilities.getText(SendTransferPage.objLastNameRequiredMsg);
				String sExpectedMsg = "Last name is required";
				Utilities.assertionValidation(sLastNameRequiredMsg,sExpectedMsg);
			}
			Thread.sleep(3000);
			Utilities.type(SendTransferPage.objLastName,prop.getproperty("Last_Name"),"Last Name Text Field");
			Utilities.type(SendTransferPage.objLastName,prop.getproperty("Last_Name"),"Last Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objMobileNumberRequiredMsg,Utilities.getTextVal(SendTransferPage.objMobileNumberRequiredMsg,"Error Message"))){
				String sMobileNumberRequiredMsg = Utilities.getText(SendTransferPage.objMobileNumberRequiredMsg);
				String sExpectedMsg = "Mobile Number is required";
				Utilities.assertionValidation(sMobileNumberRequiredMsg,sExpectedMsg);
			}
			Utilities.type(SendTransferPage.objMobileNumber,prop.getproperty("Branch_Verified"),"Last Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala,5);
			if(Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala,Utilities.getTextVal(SendTransferPage.objKwartaPadala,"page"))){
				logger.info("STB_TC_08, Prompt msg for Receiver's Details required is validated");
				ExtentReporter.extentLoggerPass("STB_TC_08", "STB_TC_08, Prompt msg for Receiver's Details required is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			backArrowBtn(3);
		}
		mlWalletLogout();
	}

	public void sendMoneyInvalidDetails() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Invalid Bank Details");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn,Utilities.getTextVal(SendTransferPage.objSendTransferBtn,"Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney,Utilities.getTextVal(SendTransferPage.objSendMoney,"Page"));
		if(Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch,Utilities.getTextVal(SendTransferPage.objToAnyMLBranch,"Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));

			Utilities.type(SendTransferPage.objFirstname,prop.getproperty("Invalid_First_Name"),"First Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objFirstNameErrorMsg,Utilities.getTextVal(SendTransferPage.objFirstNameErrorMsg,"Error Message"))){
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objFirstNameErrorMsg);
				String sExpectedMsg = "First name must only contain letters and spaces";
				Utilities.assertionValidation(sFirstNameErrorMsg,sExpectedMsg);
			}
			Utilities.type(SendTransferPage.objFirstname,prop.getproperty("First_Name"),"First Name Text Field");
			Utilities.type(SendTransferPage.objFirstname,prop.getproperty("First_Name"),"First Name Text Field");


			Utilities.type(SendTransferPage.objMiddleName,prop.getproperty("Invalid_Middle_Name"),"Middle Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objMiddleNameErrorMsg,Utilities.getTextVal(SendTransferPage.objMiddleNameErrorMsg,"Error Message"))){
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objMiddleNameErrorMsg);
				String sExpectedMsg = "Middle name must only contain letters and spaces";
				Utilities.assertionValidation(sFirstNameErrorMsg,sExpectedMsg);
			}
			Utilities.click(SendTransferPage.objCheckBox,"Check Box");
			Utilities.Swipe("UP",1);

			Utilities.type(SendTransferPage.objLastName,prop.getproperty("Invalid_Last_Name"),"Last Name Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objLastNameErrorMsg,Utilities.getTextVal(SendTransferPage.objLastNameErrorMsg,"Error Message"))){
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objLastNameErrorMsg);
				String sExpectedMsg = "Last name must only contain letters and spaces";
				Utilities.assertionValidation(sFirstNameErrorMsg,sExpectedMsg);
			}
			Utilities.type(SendTransferPage.objLastName,prop.getproperty("Last_Name"),"Last Name Text Field");
			Utilities.type(SendTransferPage.objLastName,prop.getproperty("Last_Name"),"Last Name Text Field");


			Utilities.type(SendTransferPage.objMobileNumber,prop.getproperty("Invalid_MobileNumber"),"Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objMobileNumberErrorMsg,Utilities.getTextVal(SendTransferPage.objMobileNumberErrorMsg,"Error Message"))){
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objMobileNumberErrorMsg);
				String sExpectedMsg = "Mobile number is invalid";
				Utilities.assertionValidation(sFirstNameErrorMsg,sExpectedMsg);
			}
			Utilities.clearField(SendTransferPage.objMobileNumber,"Mobile Number Text Field");
			Utilities.type(SendTransferPage.objMobileNumber,prop.getproperty("Branch_Verified"),"Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));

			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala,5);
			if(Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala,Utilities.getTextVal(SendTransferPage.objKwartaPadala,"page"))){
				logger.info("STB_TC_07, Prompt msg for Receiver's Details Invalid is validated");
				ExtentReporter.extentLoggerPass("STB_TC_07", "STB_TC_07, Prompt msg for Receiver's Details Invalid is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			backArrowBtn(3);
		}
		mlWalletLogout();
	}



	public void sendMoneyAddRecipient() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.click(SendTransferPage.objSavedRecipients,Utilities.getTextVal(SendTransferPage.objSavedRecipients,"Button"));
			Utilities.click(SendTransferPage.objAddRecipient,Utilities.getTextVal(SendTransferPage.objAddRecipient,"Button"));
			Utilities.type(SendTransferPage.objFirstname,prop.getproperty("First_Name"),"First Name Text Field");
			Utilities.type(SendTransferPage.objMiddleName,prop.getproperty("Middle_Name"),"Middle Name Text Field");
			Utilities.click(SendTransferPage.objCheckBox,"Check Box");
			Utilities.type(SendTransferPage.objLastName,prop.getproperty("Last_Name"),"Last Name Text Field");
			Utilities.type(SendTransferPage.objMobileNumber,prop.getproperty("Branch_Verified"),"Last Name Text Field");
			Utilities.type(SendTransferPage.objNickName,prop.getproperty("Nick_Name"),"Nick Name Text Field");
			Utilities.click(SendTransferPage.ObjSaveRecipient,Utilities.getTextVal(SendTransferPage.ObjSaveRecipient,"Button"));
			Utilities.type(SendTransferPage.objSearchRecipient,prop.getproperty("Last_Name"),"Search Recipient Text Field");
			if(Utilities.verifyElementPresent(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"),prop.getproperty("First_Name")),Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"),prop.getproperty("First_Name")),"Recipient"))) {
				logger.info("STB_TC_03, The Added Recipient is displayed in Saved Recipient Page");
				ExtentReporter.extentLoggerPass("STB_TC_03", "STB_TC_03, The Added Recipient is displayed in Saved Recipient Page");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
		}
		backArrowBtn(4);
		mlWalletLogout();
	}

	public void sendMoneyToSavedRecipient(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.click(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSavedRecipients, 5);
			Utilities.click(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Page"));
			Utilities.type(SendTransferPage.objSearchRecipient,prop.getproperty("Last_Name"), "Search Recipient Text Field");
			Utilities.verifyElementPresent(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"));
			Utilities.click(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"));
			Thread.sleep(3000);
			Utilities.click(SendTransferPage.objSelectRecipient,Utilities.getTextVal(SendTransferPage.objSelectRecipient,"Button"));
			Utilities.click(SendTransferPage.objCheckBox,"Check Box");
			Utilities.Swipe("UP",1);
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));

			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala,5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala,Utilities.getTextVal(SendTransferPage.objKwartaPadala,"Page"));
			Utilities.type(SendTransferPage.objAmountTxtField,Amount,"Amount text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			Utilities.verifyElementPresent(SendTransferPage.objSelectPaymentMethod,Utilities.getTextVal(SendTransferPage.objSelectPaymentMethod,"Page"));
			Utilities.click(SendTransferPage.objMLWalletBalance,Utilities.getTextVal(SendTransferPage.objMLWalletBalance,"Button"));
			Utilities.verifyElementPresent(SendTransferPage.objConfirmDetails,Utilities.getTextVal(SendTransferPage.objConfirmDetails,"Page"));
			Utilities.click(SendTransferPage.objConfirmBtn,Utilities.getTextVal(SendTransferPage.objConfirmBtn,"Button"));
			Utilities.click(SendTransferPage.objLocationPermission,Utilities.getTextVal(SendTransferPage.objLocationPermission,"Button"));
			enterOTP(prop.getproperty("Valid_OTP"));
			if(Utilities.verifyElementPresent(SendTransferPage.objSendMoneySuccessful,Utilities.getTextVal(SendTransferPage.objSendMoneySuccessful,"Message"))) {
				Utilities.verifyElementPresent(SendTransferPage.objPHPAmount, Utilities.getTextVal(SendTransferPage.objPHPAmount, "Amount"));
				Utilities.verifyElementPresent(SendTransferPage.objDate, Utilities.getTextVal(SendTransferPage.objDate, "Date"));
				Utilities.verifyElementPresent(SendTransferPage.objReferenceNumber, Utilities.getTextVal(SendTransferPage.objReferenceNumber, "Reference Number"));
				String sReference = Utilities.getText(SendTransferPage.objReferenceNumber);
				System.out.println(sReference);
				String sReferenceNumber = sReference.substring(9,20);
				System.out.println(sReferenceNumber);
				Utilities.Swipe("UP",2);
				Utilities.click(SendTransferPage.objBackToHomeBtn,Utilities.getTextVal(SendTransferPage.objBackToHomeBtn,"Button"));
				Thread.sleep(3000);
				Utilities.Swipe("DOWN",2);
				Utilities.Swipe("UP", 1);
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.click(MLWalletHomePage.objKwartaPadala, Utilities.getTextVal(MLWalletHomePage.objKwartaPadala, "Text"));
				if (Utilities.verifyElementPresent(SendTransferPage.objReferenceNumberInTransactionDetails, Utilities.getTextVal(SendTransferPage.objReferenceNumberInTransactionDetails, "Page"))) {
					String sReferenceNumberInCashOut = Utilities.getText(SendTransferPage.objReferenceNumberInTransactionDetails);
					System.out.println(sReferenceNumberInCashOut);
					Utilities.assertionValidation(sReferenceNumberInCashOut, sReferenceNumber);
					logger.info("STB_TC_02, Successfully sent Amount to saved Recipient and Transaction Details is validated");
					ExtentReporter.extentLoggerPass("STB_TC_02", "STB_TC_02, Successfully sent Amount to saved Recipient and Transaction Details is validated");
					Utilities.setScreenshotSource();
					System.out.println("-----------------------------------------------------------");
				}
				backArrowBtn(1);
			}
		}
		mlWalletLogout();
	}





	public void sendMoneyContactDuplicate() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Contact Duplicate");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.click(SendTransferPage.objSavedRecipients,Utilities.getTextVal(SendTransferPage.objSavedRecipients,"Button"));
			Utilities.click(SendTransferPage.objAddRecipient,Utilities.getTextVal(SendTransferPage.objAddRecipient,"Button"));
			Utilities.type(SendTransferPage.objFirstname,prop.getproperty("First_Name"),"First Name Text Field");
			Utilities.type(SendTransferPage.objMiddleName,prop.getproperty("Middle_Name"),"Middle Name Text Field");
			Utilities.click(SendTransferPage.objCheckBox,"Check Box");
			Utilities.type(SendTransferPage.objLastName,prop.getproperty("Last_Name"),"Last Name Text Field");
			Utilities.type(SendTransferPage.objMobileNumber,prop.getproperty("Branch_Verified"),"Mobile Number Text Field");
			Utilities.type(SendTransferPage.objNickName,prop.getproperty("Nick_Name"),"Nick Name Text Field");
			Utilities.click(SendTransferPage.ObjSaveRecipient,Utilities.getTextVal(SendTransferPage.ObjSaveRecipient,"Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objContactAlreadyExistMsg,Utilities.getTextVal(SendTransferPage.objContactAlreadyExistMsg,"Error Message"))){
				String sContactDuplicatePopupMsg = Utilities.getText(SendTransferPage.objContactAlreadyExistMsg);
				String sExpectedPopupMsg = "Contact already exists.";
				Utilities.assertionValidation(sContactDuplicatePopupMsg,sExpectedPopupMsg);
				logger.info("STB_TC_04, Contact already exists popup message Validated");
				ExtentReporter.extentLoggerPass("STB_TC_04", "STB_TC_04, Contact already exists popup message Validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			Utilities.click(SendTransferPage.objOkBtn,Utilities.getTextVal(SendTransferPage.objOkBtn,"Button"));
			Thread.sleep(3000);
			backArrowBtn(4);
		}
		mlWalletLogout();
	}



	public void sendMoneyEditRecipient() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.click(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSavedRecipients, 5);
			Utilities.click(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Page"));
			Thread.sleep(3000);

			Utilities.type(SendTransferPage.objSearchRecipient, prop.getproperty("Last_Name"), "Search Recipient Text Field");
			Utilities.verifyElementPresent(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"));
			Utilities.click(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Last_Name"), prop.getproperty("First_Name")), "Recipient"));
			Utilities.click(SendTransferPage.objEditRecipient,Utilities.getTextVal(SendTransferPage.objEditRecipient,"Button"));
			Utilities.type(SendTransferPage.objEditRecipientLastName,prop.getproperty("Edited_Last_name"),"Last Name Text Field");
			Utilities.click(SendTransferPage.ObjSaveRecipient,Utilities.getTextVal(SendTransferPage.ObjSaveRecipient,"Button"));
			Utilities.type(SendTransferPage.objSearchRecipient,prop.getproperty("Edited_Last_name"),"Search Recipient Text Field");
			if(Utilities.verifyElementPresent(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"),prop.getproperty("First_Name")),Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"),prop.getproperty("First_Name")),"Recipient"))){
				logger.info("STB_TC_06, Successfully edited the Saved Recipient");
				ExtentReporter.extentLoggerPass("STB_TC_06", "STB_TC_06, Successfully edited the Saved Recipient");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			backArrowBtn(3);
		}
		mlWalletLogout();
	}



	public void sendMoneyDeleteRecipient() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.click(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSavedRecipients,5);
			Utilities.click(SendTransferPage.objSavedRecipients, Utilities.getTextVal(SendTransferPage.objSavedRecipients, "Page"));

			Utilities.type(SendTransferPage.objSearchRecipient,prop.getproperty("Edited_Last_name"),"Search Recipient Text Field");
			Utilities.verifyElementPresent(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"),prop.getproperty("First_Name")),Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"),prop.getproperty("First_Name")),"Recipient"));
			Utilities.click(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"),prop.getproperty("First_Name")),Utilities.getTextVal(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"),prop.getproperty("First_Name")),"Recipient"));
			Utilities.click(SendTransferPage.objDeleteRecipient,Utilities.getTextVal(SendTransferPage.objDeleteRecipient,"Button"));
			Utilities.verifyElementPresent(SendTransferPage.objPopupMsg,Utilities.getTextVal(SendTransferPage.objPopupMsg,"Pop Up message"));
			String sDeleteConfirmationPopup = Utilities.getText(SendTransferPage.objPopupMsg);
			String sExceptedMsg = "Are you sure you want to remove this saved recipient?";
			Utilities.assertionValidation(sDeleteConfirmationPopup,sExceptedMsg);
			Utilities.click(SendTransferPage.objRemoveBtn,Utilities.getTextVal(SendTransferPage.objRemoveBtn,"Button"));
			Utilities.clearField(SendTransferPage.objSearchRecipient,"Search Field");
			Thread.sleep(3000);
			if(Utilities.verifyElementNotPresent(SendTransferPage.objSelectLastName(prop.getproperty("Edited_Last_name"),prop.getproperty("First_Name")),5)){
				logger.info("STB_TC_05, Saved Recipient from Saved Recipients page not got deleted Successfully");
			}else{
				logger.info("STB_TC_05, Saved Recipient from Saved Recipients page deleted Successfully");
				ExtentReporter.extentLoggerPass("STB_TC_05", "STB_TC_05, Saved Recipient from Saved Recipients page deleted Successfully");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			backArrowBtn(4);
		}
		mlWalletLogout();
	}

	public void sendMoneyInvalidAmount(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
			Utilities.type(SendTransferPage.objMiddleName, prop.getproperty("Middle_Name"), "Middle Name Text Field");
			Utilities.click(SendTransferPage.objCheckBox, "Check Box");
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.type(SendTransferPage.objAmountTxtField, Amount, "Amount text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objInvalidAmountMsg,Utilities.getTextVal(SendTransferPage.objInvalidAmountMsg,"Error Message"))){
				String sInvalidAmountErrorMsg = Utilities.getText(SendTransferPage.objInvalidAmountMsg);
				String sExpectedErrorMsg = "The amount should not be less than 1";
				Utilities.assertionValidation(sInvalidAmountErrorMsg,sExpectedErrorMsg);
				logger.info("STB_TC_09, The amount should not be less than 1 - Error Message is validated");
				ExtentReporter.extentLoggerPass("STB_TC_09", "STB_TC_09, The amount should not be less than 1 - Error Message is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			backArrowBtn(3);
		}
		mlWalletLogout();
	}

	public void sendMoneyInsufficientAmount(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
			Utilities.type(SendTransferPage.objMiddleName, prop.getproperty("Middle_Name"), "Middle Name Text Field");
			Utilities.click(SendTransferPage.objCheckBox, "Check Box");
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.type(SendTransferPage.objAmountTxtField, Amount, "Amount text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			Utilities.verifyElementPresent(SendTransferPage.objSelectPaymentMethod,Utilities.getTextVal(SendTransferPage.objSelectPaymentMethod,"Page"));
			Utilities.explicitWaitVisible(SendTransferPage.objMLWalletBalance,5);
			Utilities.click(SendTransferPage.objMLWalletBalance,Utilities.getTextVal(SendTransferPage.objMLWalletBalance,"Button"));
			Utilities.verifyElementPresent(SendTransferPage.objConfirmDetails,Utilities.getTextVal(SendTransferPage.objConfirmDetails,"Page"));
			Utilities.click(SendTransferPage.objConfirmBtn,Utilities.getTextVal(SendTransferPage.objConfirmBtn,"Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objInsufficientAmountMsg,Utilities.getTextVal(SendTransferPage.objInsufficientAmountMsg,"Error Message"))){
				String sInsufficientBalanceErrorMsg = Utilities.getText(SendTransferPage.objInsufficientAmountMsg);
				String sExpectedErrorMsg = "There is insufficient balance to proceed with this transaction. Please try again.";
				Utilities.assertionValidation(sInsufficientBalanceErrorMsg,sExpectedErrorMsg);
				logger.info("STB_TC_10, Insufficient Balance - Error Message is validated");
				ExtentReporter.extentLoggerPass("STB_TC_10", "STB_TC_10, Insufficient Balance - Error Message is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			Utilities.click(SendTransferPage.objOkBtn,Utilities.getTextVal(SendTransferPage.objOkBtn,"Button"));
			Thread.sleep(2000);
			backArrowBtn(5);
		}
		mlWalletLogout();
	}


	public void sendMoneyMaximumAmount(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Fully_verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"))) {
			Utilities.click(SendTransferPage.objToAnyMLBranch, Utilities.getTextVal(SendTransferPage.objToAnyMLBranch, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.type(SendTransferPage.objFirstname, prop.getproperty("First_Name"), "First Name Text Field");
			Utilities.type(SendTransferPage.objMiddleName, prop.getproperty("Middle_Name"), "Middle Name Text Field");
			Utilities.click(SendTransferPage.objCheckBox, "Check Box");
			Utilities.type(SendTransferPage.objLastName, prop.getproperty("Last_Name"), "Last Name Text Field");
			Utilities.type(SendTransferPage.objMobileNumber, prop.getproperty("Branch_Verified"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objKwartaPadala, 5);
			Utilities.verifyElementPresent(SendTransferPage.objKwartaPadala, Utilities.getTextVal(SendTransferPage.objKwartaPadala, "Page"));
			Utilities.type(SendTransferPage.objAmountTxtField, Amount, "Amount text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			Utilities.verifyElementPresent(SendTransferPage.objSelectPaymentMethod,Utilities.getTextVal(SendTransferPage.objSelectPaymentMethod,"Page"));
			Utilities.explicitWaitVisible(SendTransferPage.objMLWalletBalance,5);
			Utilities.click(SendTransferPage.objMLWalletBalance,Utilities.getTextVal(SendTransferPage.objMLWalletBalance,"Button"));
			Utilities.verifyElementPresent(SendTransferPage.objConfirmDetails,Utilities.getTextVal(SendTransferPage.objConfirmDetails,"Page"));
			Utilities.click(SendTransferPage.objConfirmBtn,Utilities.getTextVal(SendTransferPage.objConfirmBtn,"Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objMaxLimitErrorMsg,Utilities.getTextVal(SendTransferPage.objMaxLimitErrorMsg,"Error Message"))){
				String sMaximumLimitErrorMsg = Utilities.getText(SendTransferPage.objMaxLimitErrorMsg);
				String sExpectedErrorMsg = "The maximum Send Money per transaction set for your verification level is P50,000.00. Please try again.";
				Utilities.assertionValidation(sMaximumLimitErrorMsg,sExpectedErrorMsg);
				logger.info("STB_TC_12, The maximum send money per transaction - Error Message is validated");
				ExtentReporter.extentLoggerPass("STB_TC_12", "STB_TC_12, The maximum send money per transaction - Error Message is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			Utilities.click(SendTransferPage.objOkBtn,Utilities.getTextVal(SendTransferPage.objOkBtn,"Button"));
			Thread.sleep(2000);
			backArrowBtn(5);
		}
		mlWalletLogout();
	}
	public void backArrowBtn(int nNumber) throws Exception {
		for (int i=1;i<=nNumber;i++){
			Utilities.click(SendTransferPage.objBackArrow,"Back Arrow Button");
			Thread.sleep(2000);
		}
	}


//===============================================Send/Transfer To a ML Wallet User=============================//

	public void sendToMLWalletUser(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"))) {
			Utilities.click(SendTransferPage.objToAMLWalletUser,Utilities.getTextVal(SendTransferPage.objToAMLWalletUser,"Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSendMoney,5);
			Utilities.verifyElementPresent(SendTransferPage.objSendMoney,Utilities.getTextVal(SendTransferPage.objSendMoney,"Page"));
			Utilities.type(SendTransferPage.objMobileNumberField,prop.getproperty("Fully_verified"),"Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objAmountTxtField,5);
			Utilities.type(SendTransferPage.objAmountTxtField,Amount,"Amount Text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			Utilities.click(SendTransferPage.objMLWalletBalance,Utilities.getTextVal(SendTransferPage.objMLWalletBalance,"Button"));
			Utilities.verifyElementPresent(SendTransferPage.objConfirmDetails,Utilities.getTextVal(SendTransferPage.objConfirmDetails,"Page"));
			Utilities.Swipe("UP",1);
			Utilities.click(SendTransferPage.objSendPHPBtn,Utilities.getTextVal(SendTransferPage.objSendPHPBtn,"Button"));
			Utilities.click(SendTransferPage.objLocationPermission,Utilities.getTextVal(SendTransferPage.objLocationPermission,"Button"));
			enterOTP(prop.getproperty("Valid_OTP"));
			if(Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWallet,Utilities.getTextVal(SendTransferPage.objSendMoneyMLWallet,"Message"))) {
				Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletPHP, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletPHP, "Amount"));
				Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletDate, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletDate, "Date"));
				Utilities.verifyElementPresent(SendTransferPage.objMLWalletReferenceNumber, Utilities.getTextVal(SendTransferPage.objMLWalletReferenceNumber, "Reference Number"));
				String sReferenceNumber = Utilities.getText(SendTransferPage.objMLWalletReferenceNumber);
				System.out.println(sReferenceNumber);
				Utilities.Swipe("UP",2);
				Utilities.click(SendTransferPage.objBackToHomeBtn,Utilities.getTextVal(SendTransferPage.objBackToHomeBtn,"Button"));
				Thread.sleep(3000);
				Utilities.Swipe("DOWN",2);
				Utilities.Swipe("UP", 1);
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.verifyElementPresent(MLWalletHomePage.objWalletToWallet, Utilities.getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
				Utilities.click(MLWalletHomePage.objWalletToWallet, Utilities.getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
				if (Utilities.verifyElementPresent(SendTransferPage.objReferenceNumberInTransactionDetails, Utilities.getTextVal(SendTransferPage.objReferenceNumberInTransactionDetails, "Page"))) {
					String sReferenceNumberInWalletToWallet = Utilities.getText(SendTransferPage.objReferenceNumberInTransactionDetails);
					System.out.println(sReferenceNumberInWalletToWallet);
					Utilities.assertionValidation(sReferenceNumberInWalletToWallet, sReferenceNumber);
					logger.info("STW_TC_01, Successfully Amount sent from Wallet to Wallet and Transaction Details is validated");
					ExtentReporter.extentLoggerPass("STW_TC_01", "STW_TC_01, Successfully Amount sent from Wallet to Wallet and Transaction Details is validated");
					Utilities.setScreenshotSource();
					System.out.println("-----------------------------------------------------------");
				}
				backArrowBtn(1);
			}
		}
		mlWalletLogout();
	}


	public void sendMoneyAddToFavorites(String Amount) throws Exception {
//		ExtentReporter.HeaderChildNode("Send Money Add To Favorites");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"))) {
			Utilities.click(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSendMoney, 5);
			Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
			Utilities.type(SendTransferPage.objMobileNumberField, prop.getproperty("Fully_verified"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objAmountTxtField, 5);
			Utilities.type(SendTransferPage.objAmountTxtField, Amount, "Amount Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.click(SendTransferPage.objMLWalletBalance, Utilities.getTextVal(SendTransferPage.objMLWalletBalance, "Button"));
			Utilities.verifyElementPresent(SendTransferPage.objConfirmDetails, Utilities.getTextVal(SendTransferPage.objConfirmDetails, "Page"));
			Utilities.click(SendTransferPage.objSendPHPBtn, Utilities.getTextVal(SendTransferPage.objSendPHPBtn, "Button"));
//			Utilities.click(SendTransferPage.objLocationPermission, Utilities.getTextVal(SendTransferPage.objLocationPermission, "Button"));
			enterOTP(prop.getproperty("Valid_OTP"));
			if (Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWallet, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWallet, "Message"))) {
				Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletPHP, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletPHP, "Amount"));
				Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletDate, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletDate, "Date"));
				Utilities.verifyElementPresent(SendTransferPage.objMLWalletReferenceNumber, Utilities.getTextVal(SendTransferPage.objMLWalletReferenceNumber, "Reference Number"));
				String sReferenceNumber = Utilities.getText(SendTransferPage.objMLWalletReferenceNumber);
				System.out.println(sReferenceNumber);
				Utilities.Swipe("UP", 1);
				Utilities.click(SendTransferPage.objSaveToMyFavorite,Utilities.getTextVal(SendTransferPage.objSaveToMyFavorite,"Button"));
				if(Utilities.verifyElementPresent(SendTransferPage.objAddedToFavoritesMsg,Utilities.getTextVal(SendTransferPage.objAddedToFavoritesMsg,"Message"))){
					Utilities.click(SendTransferPage.objOkBtn,Utilities.getTextVal(SendTransferPage.objOkBtn,"Button"));
				}
			}
			backArrowBtn(2);
		}
		mlWalletLogout();
	}

	public void sendMoneyMLWalletToExistingReceiver(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money ML Wallet To Existing Receiver");
		sendMoneyAddToFavorites("10");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"))) {
			Utilities.click(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSendMoney, 5);
			Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
			Utilities.verifyElementPresent(SendTransferPage.objSelectFavorite,Utilities.getTextVal(SendTransferPage.objSelectFavorite,"Text"));
			Utilities.click(SendTransferPage.objSelectFavorite,Utilities.getTextVal(SendTransferPage.objSelectFavorite,"Text"));
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objAmountTxtField,5);
			Utilities.type(SendTransferPage.objAmountTxtField,Amount,"Amount Text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			Utilities.click(SendTransferPage.objMLWalletBalance,Utilities.getTextVal(SendTransferPage.objMLWalletBalance,"Button"));
			Utilities.verifyElementPresent(SendTransferPage.objConfirmDetails,Utilities.getTextVal(SendTransferPage.objConfirmDetails,"Page"));
			Utilities.Swipe("UP",1);
			Utilities.click(SendTransferPage.objSendPHPBtn,Utilities.getTextVal(SendTransferPage.objSendPHPBtn,"Button"));
//			Utilities.click(SendTransferPage.objLocationPermission,Utilities.getTextVal(SendTransferPage.objLocationPermission,"Button"));
			enterOTP(prop.getproperty("Valid_OTP"));
			if(Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWallet,Utilities.getTextVal(SendTransferPage.objSendMoneyMLWallet,"Message"))) {
				Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletPHP, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletPHP, "Amount"));
				Utilities.verifyElementPresent(SendTransferPage.objSendMoneyMLWalletDate, Utilities.getTextVal(SendTransferPage.objSendMoneyMLWalletDate, "Date"));
				Utilities.verifyElementPresent(SendTransferPage.objMLWalletReferenceNumber, Utilities.getTextVal(SendTransferPage.objMLWalletReferenceNumber, "Reference Number"));
				String sReferenceNumber = Utilities.getText(SendTransferPage.objMLWalletReferenceNumber);
				System.out.println(sReferenceNumber);
				Utilities.Swipe("UP",2);
				Utilities.click(SendTransferPage.objBackToHomeBtn,Utilities.getTextVal(SendTransferPage.objBackToHomeBtn,"Button"));
				Thread.sleep(3000);
				Utilities.Swipe("DOWN",2);
				Utilities.Swipe("UP", 1);
				Utilities.verifyElementPresent(MLWalletHomePage.objRecentTransactions, Utilities.getTextVal(MLWalletHomePage.objRecentTransactions, "Text"));
				Utilities.verifyElementPresent(MLWalletHomePage.objWalletToWallet, Utilities.getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
				Utilities.click(MLWalletHomePage.objWalletToWallet, Utilities.getTextVal(MLWalletHomePage.objWalletToWallet, "Text"));
				if (Utilities.verifyElementPresent(SendTransferPage.objReferenceNumberInTransactionDetails, Utilities.getTextVal(SendTransferPage.objReferenceNumberInTransactionDetails, "Page"))) {
					String sReferenceNumberInWalletToWallet = Utilities.getText(SendTransferPage.objReferenceNumberInTransactionDetails);
					System.out.println(sReferenceNumberInWalletToWallet);
					Utilities.assertionValidation(sReferenceNumberInWalletToWallet, sReferenceNumber);
					logger.info("STW_TC_02, Successfully Amount sent from Wallet to Wallet to Recently added favorite and Transaction Details is validated");
					ExtentReporter.extentLoggerPass("STW_TC_02", "STW_TC_02, Successfully Amount sent from Wallet to Wallet to Recently added favorite and Transaction Details is validated");
					Utilities.setScreenshotSource();
					System.out.println("-----------------------------------------------------------");
				}
				backArrowBtn(3);
			}
		}
		mlWalletLogout();
	}


	public void sendToMLWalletInvalidMobNumber() throws Exception {
		ExtentReporter.HeaderChildNode("Send To ML Wallet to Invalid Mobile Number");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"))) {
			Utilities.click(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSendMoney, 5);
			Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
			Utilities.type(SendTransferPage.objMobileNumberField, prop.getproperty("Invalid_MobileNumber"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objMobileNumberErrorMsg,Utilities.getTextVal(SendTransferPage.objMobileNumberErrorMsg,"Error Message"))){
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objMobileNumberErrorMsg);
				String sExpectedMsg = "Mobile number is invalid";
				Utilities.assertionValidation(sFirstNameErrorMsg,sExpectedMsg);
				logger.info("STW_TC_03, Mobile number is invalid - Error Message is validated");
				ExtentReporter.extentLoggerPass("STW_TC_03", "STW_TC_03, Mobile number is invalid - Error Message is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			backArrowBtn(2);
		}
		mlWalletLogout();
	}

	public void sendToMLWalletUnRegisteredNumber() throws Exception {
		ExtentReporter.HeaderChildNode("Send To ML Wallet to Invalid Mobile Number");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"))) {
			Utilities.click(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSendMoney, 5);
			Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
			Utilities.type(SendTransferPage.objMobileNumberField, prop.getproperty("Unregistered_MobileNumber"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));

			if(Utilities.verifyElementPresent(SendTransferPage.objUnRegisteredMobNumber,Utilities.getTextVal(SendTransferPage.objUnRegisteredMobNumber,"Error Message"))){
				String sFirstNameErrorMsg = Utilities.getText(SendTransferPage.objUnRegisteredMobNumber);
				String sExpectedMsg = "Receiver not Found!";
				Utilities.assertionValidation(sFirstNameErrorMsg,sExpectedMsg);
				logger.info("STW_TC_04, Receiver not Found - Error Message is validated");
				ExtentReporter.extentLoggerPass("STW_TC_04", "STW_TC_04, Receiver not Found - Error Message is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			backArrowBtn(3);
		}
		mlWalletLogout();
	}


	public void sendToMLWalletInvalidAmount(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"))) {
			Utilities.click(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSendMoney, 5);
			Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
			Utilities.type(SendTransferPage.objMobileNumberField, prop.getproperty("Fully_verified"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objAmountTxtField, 5);
			Utilities.type(SendTransferPage.objAmountTxtField, Amount, "Amount Text Field");
			Utilities.click(SendTransferPage.objNextBtn,Utilities.getTextVal(SendTransferPage.objNextBtn,"Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objInvalidAmountMsg,Utilities.getTextVal(SendTransferPage.objInvalidAmountMsg,"Error Message"))){
				String sInvalidAmountErrorMsg = Utilities.getText(SendTransferPage.objInvalidAmountMsg);
				String sExpectedErrorMsg = "The amount should not be less than 1";
				Utilities.assertionValidation(sInvalidAmountErrorMsg,sExpectedErrorMsg);
				logger.info("STW_TC_05, The amount should not be less than 1 - Error Message is validated");
				ExtentReporter.extentLoggerPass("STW_TC_05", "STW_TC_05, The amount should not be less than 1 - Error Message is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			backArrowBtn(3);
		}
		mlWalletLogout();
	}


	public void sendToMLWalletInsufficientAmount(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money to any ML Branch");
		mlWalletLogin("9999999997");
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"))) {
			Utilities.click(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSendMoney, 5);
			Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
			Utilities.type(SendTransferPage.objMobileNumberField, prop.getproperty("Branch_Verified"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objAmountTxtField, 5);
			Utilities.type(SendTransferPage.objAmountTxtField, Amount, "Amount Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.click(SendTransferPage.objMLWalletBalance, Utilities.getTextVal(SendTransferPage.objMLWalletBalance, "Button"));
			Utilities.verifyElementPresent(SendTransferPage.objConfirmDetails, Utilities.getTextVal(SendTransferPage.objConfirmDetails, "Page"));
			Utilities.Swipe("UP", 1);
			Utilities.click(SendTransferPage.objSendPHPBtn, Utilities.getTextVal(SendTransferPage.objSendPHPBtn, "Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objInsufficientAmountMsg,Utilities.getTextVal(SendTransferPage.objInsufficientAmountMsg,"Error Message"))){
				String sInsufficientBalanceErrorMsg = Utilities.getText(SendTransferPage.objInsufficientAmountMsg);
				String sExpectedErrorMsg = "There is insufficient balance to proceed with this transaction. Please try again.";
				Utilities.assertionValidation(sInsufficientBalanceErrorMsg,sExpectedErrorMsg);
				logger.info("STW_TC_06, Insufficient Balance - Error Message is validated");
				ExtentReporter.extentLoggerPass("STW_TC_06", "STW_TC_06, Insufficient Balance - Error Message is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			backArrowBtn(6);
		}
		mlWalletLogout();
	}

	public void sendMoneyMLWalletMaximumAmount(String Amount) throws Exception {
		ExtentReporter.HeaderChildNode("Send Money ML Wallet Maximum Amount");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"))) {
			Utilities.click(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSendMoney, 5);
			Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
			Utilities.type(SendTransferPage.objMobileNumberField, prop.getproperty("Fully_verified"), "Mobile Number Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objAmountTxtField, 5);
			Utilities.type(SendTransferPage.objAmountTxtField, Amount, "Amount Text Field");
			Utilities.click(SendTransferPage.objNextBtn, Utilities.getTextVal(SendTransferPage.objNextBtn, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objMLWalletBalance,5);
			Utilities.click(SendTransferPage.objMLWalletBalance,Utilities.getTextVal(SendTransferPage.objMLWalletBalance,"Button"));
			Utilities.verifyElementPresent(SendTransferPage.objConfirmDetails,Utilities.getTextVal(SendTransferPage.objConfirmDetails,"Page"));
			Utilities.Swipe("UP",1);
			Utilities.click(SendTransferPage.objSendPHPBtn, Utilities.getTextVal(SendTransferPage.objSendPHPBtn, "Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objMaxLimitErrorMsg,Utilities.getTextVal(SendTransferPage.objMaxLimitErrorMsg,"Error Message"))){
				String sMaximumLimitErrorMsg = Utilities.getText(SendTransferPage.objMaxLimitErrorMsg);
				String sExpectedErrorMsg = "The maximum Send Money per transaction set for your verification level is P50,000.00. Please try again.";
				Utilities.assertionValidation(sMaximumLimitErrorMsg,sExpectedErrorMsg);
				logger.info("STW_TC_07, The maximum send money per transaction - Error Message is validated");
				ExtentReporter.extentLoggerPass("STW_TC_07", "STW_TC_07, The maximum send money per transaction - Error Message is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			backArrowBtn(6);
		}
		mlWalletLogout();
	}


	public void sendMoneyDeleteFromFavorites() throws Exception {
		ExtentReporter.HeaderChildNode("Send Money Delete From Favorites");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.click(SendTransferPage.objSendTransferBtn, Utilities.getTextVal(SendTransferPage.objSendTransferBtn, "Button"));
		Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
		if (Utilities.verifyElementPresent(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"))) {
			Utilities.click(SendTransferPage.objToAMLWalletUser, Utilities.getTextVal(SendTransferPage.objToAMLWalletUser, "Button"));
			Utilities.explicitWaitVisible(SendTransferPage.objSendMoney, 5);
			Utilities.verifyElementPresent(SendTransferPage.objSendMoney, Utilities.getTextVal(SendTransferPage.objSendMoney, "Page"));
			Utilities.click(SendTransferPage.objViewAllBtn,Utilities.getTextVal(SendTransferPage.objViewAllBtn,"Text"));
			Utilities.click(SendTransferPage.objEllipsisBtn,"Ellipsis Button");
			Utilities.click(SendTransferPage.objDeleteBtn,Utilities.getTextVal(SendTransferPage.objDeleteBtn,"Button"));
			Utilities.click(SendTransferPage.objConfirmBtn,Utilities.getTextVal(SendTransferPage.objConfirmBtn,"Button"));
			if(Utilities.verifyElementPresent(SendTransferPage.objFavRemovedMsg,Utilities.getTextVal(SendTransferPage.objFavRemovedMsg,"Pop up Message"))){
				String sRemovedSuccessfulMsg = Utilities.getText(SendTransferPage.objFavRemovedMsg);
				String sExpectedMsg = "Successfully Removed";
				Utilities.assertionValidation(sRemovedSuccessfulMsg,sExpectedMsg);
				logger.info("STW_TC_09, Successfully removed Favorite Contact from favorites list is validated");
				ExtentReporter.extentLoggerPass("STW_TC_09", "STW_TC_09, Successfully removed Favorite Contact from favorites list is validated");
				Utilities.setScreenshotSource();
				System.out.println("-----------------------------------------------------------");
			}
			Utilities.click(SendTransferPage.objOkBtn,Utilities.getTextVal(SendTransferPage.objOkBtn,"Button"));
			backArrowBtn(3);
		}
		mlWalletLogout();
	}



//================================================ Transaction History ===========================================//



	public void mlWallet_TransactionHistory_Generic_Steps(String billModule, String transaction) throws Exception {
		String PayBillsHistory = Utilities.getText(MLWalletTransactionHistoryPage.objBillHistory(billModule, transaction));
		if (PayBillsHistory.equals(billModule))// "Pay Bills"
		{
			List<WebElement> values = Utilities
					.findElements(MLWalletTransactionHistoryPage.objPayBillsTransctionList1(billModule));
			for (int i = 0; i < values.size(); i++) {
				String billPayTransaction = values.get(i).getText();
				logger.info(billModule + " All Transactions : " + billPayTransaction);
				ExtentReporter.extentLogger(" ", billModule + " All Transactions : " + billPayTransaction);
			}
		} else if (PayBillsHistory.equals(transaction))// "No Recent Transaction"
		{
			logger.info("No Recent Transactions Are Available for " + billModule + " Module");
			ExtentReporter.extentLogger("", "No Recent Transactions Are Available for " + billModule + " Module");
		}
	}

	public void mlWallet_TransactionHistory() throws Exception {
		ExtentReporter.HeaderChildNode("MLWallet_TransactionHistory_Scenario");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		Utilities.verifyElementPresent(MLWalletTransactionHistoryPage.objRecentTransaction,Utilities.getText(MLWalletTransactionHistoryPage.objRecentTransaction));
		Utilities.Swipe("UP", 2);
		Utilities.click(MLWalletTransactionHistoryPage.objSeeMoreBtn, "See More Button");
		logger.info("TH_TC_01, All Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_01", "'TH_TC_01', All Transactions are displayed");

		Utilities.click(MLWalletTransactionHistoryPage.objBillsPayTab, "Bills Pay");
		mlWallet_TransactionHistory_Generic_Steps("Pay Bills", "No Recent Transaction");
		logger.info("TH_TC_02, Bills pay Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_02", "'TH_TC_02', Bills pay Transactions are displayed");

		Utilities.click(MLWalletTransactionHistoryPage.objeLoadTab, "eLoad");
		mlWallet_TransactionHistory_Generic_Steps("Buy Eload", "No Recent Transaction");
		logger.info("TH_TC_03, eLoad Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_03", "'TH_TC_03', eLoad Transactions Transactions are displayed");


		Utilities.click(MLWalletTransactionHistoryPage.objSendMoneyTab, "Send Money");
		mlWallet_TransactionHistory_Generic_Steps("Wallet to Wallet", "No Recent Transaction"); // Kwarta Padala
		logger.info("TH_TC_04, Send Money Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_04", "'TH_TC_04', Send Money Transactions are displayed");


		Utilities.scrollToFirstHorizontalScrollableElement("Receive Money");
		Utilities.click(MLWalletTransactionHistoryPage.objCashInTab, "Cash In");
		Thread.sleep(3000);
		mlWallet_TransactionHistory_Generic_Steps("Cash In", "No Recent Transaction");
		logger.info("TH_TC_05, Cash In Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_05", "'TH_TC_05',  Cash In Transactions are displayed");


		Utilities.click(MLWalletTransactionHistoryPage.objCashOutTab, "Cash Out");
		Thread.sleep(3000);
		mlWallet_TransactionHistory_Generic_Steps("Cash Out", "No Recent Transaction");
		logger.info("TH_TC_06,  Cash Out Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_06", "'TH_TC_06', Cash Out Transactions are displayed");

		Utilities.click(MLWalletTransactionHistoryPage.objReceiveMoneyTab, "Receive Money");
		Thread.sleep(3000);
		mlWallet_TransactionHistory_Generic_Steps("Receive Money", "No Recent Transaction");
		logger.info("TH_TC_07, Receive Money Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_07", "'TH_TC_07', Receive Money Transactions are displayed");

		Utilities.scrollToFirstHorizontalScrollableElement("ML Shop");
		Utilities.click(MLWalletTransactionHistoryPage.objBalanceAdjustmentTab, "Balance Adjustment");
		Thread.sleep(2000);
		mlWallet_TransactionHistory_Generic_Steps("Balance Adjustment", "No Recent Transaction");
		logger.info("TH_TC_08, Balance Adjustment Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_08", "'TH_TC_08', Balance Adjustment Transactions are displayed");
		Thread.sleep(2000);
		Utilities.click(MLWalletTransactionHistoryPage.objMlShopTab, "ML Shop");
		Thread.sleep(2000);
		mlWallet_TransactionHistory_Generic_Steps("ML Shop", "No Recent Transaction");
		logger.info("TH_TC_09, ML Shop Transactions are displayed");
		ExtentReporter.extentLoggerPass("TH_TC_09", "'TH_TC_09', ML Shop Transactions are displayed");

		Utilities.click(MLWalletShopItemsPage.objBackArrowBtn, "Back Arrow Button");
		Utilities.Swipe("down", 2);
		if (Utilities.verifyElementPresent(MLWalletShopItemsPage.objAvailableBalance, "Available Balance")) {
			logger.info("Navigated to Home Page");
			ExtentReporter.extentLogger("", "Navigated to Home Page");
		} else {
			logger.info("Failed to Navigate Home Page");
			ExtentReporter.extentLogger("", "Failed to Navigate Home Page");
		}

		mlWalletLogout();

	}

//=================================== ML Wallet Shop Items ==========================================================//


	public void mlWallet_ShopItems_Generic_Steps() throws Exception {
		// ExtentReporter.HeaderChildNode("Shop_Items");
		Utilities.click(MLWalletShopItemsPage.objShopItemsTab, "Shop Items Icon");
		Utilities.verifyElementPresentAndClick(MLWalletShopItemsPage.objMLShopPage, "ML Shop Page");
		Thread.sleep(5000);
		Utilities.Swipe("UP",2);
		Utilities.click(MLWalletShopItemsPage.objItemMenu, "Rings Item");
		Utilities.click(MLWalletShopItemsPage.objSelectItem,Utilities.getTextVal(MLWalletShopItemsPage.objSelectItem, "Item"));
		Utilities.Swipe("up", 2);
		Utilities.click(MLWalletShopItemsPage.objAddToCartBtn, "Add to cart Button");
		Utilities.Swipe("down", 4);
		Utilities.click(MLWalletShopItemsPage.objHambergerMenu, "Hamburger Menu");
		Utilities.click(MLWalletShopItemsPage.objyourBagMenu, "Your Bag");
		Utilities.click(MLWalletShopItemsPage.objCheckBox, "Check Box");
		Utilities.click(MLWalletShopItemsPage.objCheckOutBtn, "Checkout Button");
		Utilities.click(MLWalletShopItemsPage.objEditAddress, "Edit Address Tab");
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objSelectBranchPage,Utilities.getTextVal(MLWalletShopItemsPage.objSelectBranchPage, "Page"));
		Utilities.click(MLWalletShopItemsPage.objInputFieldOne, "Select Branch Field 1");
		Utilities.click(MLWalletShopItemsPage.objBranchName,Utilities.getTextVal(MLWalletShopItemsPage.objBranchName, "Branch Name"));
		Utilities.click(MLWalletShopItemsPage.objInputFieldTwo, "Select Branch Field 2");
		Utilities.click(MLWalletShopItemsPage.objSubBranchName,Utilities.getTextVal(MLWalletShopItemsPage.objSubBranchName, "Branch Name"));
		Utilities.click(MLWalletShopItemsPage.objInputFieldThree, "Select Branch Field 3");
		Utilities.click(MLWalletShopItemsPage.objSubBranchNameTwo,Utilities.getTextVal(MLWalletShopItemsPage.objSubBranchNameTwo, "Branch Name"));
		Utilities.click(MLWalletShopItemsPage.objSaveBtn, "Save Button");
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objAddressSuccessfulMsg,Utilities.getTextVal(MLWalletShopItemsPage.objAddressSuccessfulMsg, "Message"));
		Utilities.click(MLWalletShopItemsPage.objOkBtn, "OK Button");
		Utilities.scrollToVertical("Place Order");
		Utilities.click(MLWalletShopItemsPage.objPlaceOrderBtn, "Place Order Button");

	}

	public void mlWallet_ShopItems_Successful_Purchase() throws Exception {
		ExtentReporter.HeaderChildNode("mlWalletShopItems_Successful_Purchase");
		mlWallet_ShopItems_Generic_Steps();
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objOtpPage,Utilities.getTextVal(MLWalletShopItemsPage.objOtpPage, "Pop up"));
		Thread.sleep(2000);
		Utilities.click(MLWalletShopItemsPage.objOtpTextField, "Otp Text Field");
		Utilities.handleOtp(prop.getproperty("otp"));
		Utilities.click(MLWalletShopItemsPage.objValidateBtn, "Validate Button");
		// code for successful purchase message validation
	}

	public void mlWallet_ShopItems_with_Insufficient_Balance() throws Exception {
		ExtentReporter.HeaderChildNode("mlWallet_ShopItems_with_Insufficient_Balance");
		mlWalletLogin(prop.getproperty("Buyer_Tier"));
		mlWallet_ShopItems_Generic_Steps();
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objOtpPage,Utilities.getTextVal(MLWalletShopItemsPage.objOtpPage, "Pop up"));
		Thread.sleep(2000);
		Utilities.click(MLWalletShopItemsPage.objOtpTextField, "Otp Text Field");
		Utilities.handleOtp(prop.getproperty("OTP"));
		Utilities.click(MLWalletShopItemsPage.objValidateBtn, "Validate Button");
		String oOpsMsg = Utilities.getText(MLWalletShopItemsPage.objInvalidOtpPopUp);
		String supplyFieldsMsg = Utilities.getText(MLWalletShopItemsPage.objInvalidOtpPopUpMsg);
		logger.info(oOpsMsg + " " + supplyFieldsMsg + " Pop Up Message is displayed");
		ExtentReporter.extentLogger("", oOpsMsg + " " + supplyFieldsMsg + " Pop Up Message is displayed");
		logger.info("MLS_TC_02, Oops... Insufficient Balance. - Error message is validated ");
		ExtentReporter.extentLoggerPass("MLS_TC_02", "MLS_TC_02, Oops... Insufficient Balance. - Error message is validated");
		Utilities.setScreenshotSource();
		System.out.println("-----------------------------------------------------------");

		Utilities.click(MLWalletShopItemsPage.objOkBtn, "OK Button");
		Utilities.click(MLWalletShopItemsPage.objBackArrowBtn, "Back Arrow Button");
		if (Utilities.verifyElementPresent(MLWalletShopItemsPage.objAvailableBalance, "Available Balance")) {
			logger.info("Navigated to Home Page");
			ExtentReporter.extentLogger("", "Navigated to Home Page");
		} else {
			logger.info("Failed to Navigate Home Page");
			ExtentReporter.extentLogger("", "Failed to Navigate Home Page");
		}
		mlWalletLogout();
	}

	public void mlWallet_ShopItems_with_Incorrect_Otp() throws Exception {
		ExtentReporter.HeaderChildNode("mlWallet_ShopItems_with_Incorrect_Otp");
		mlWallet_ShopItems_Generic_Steps();
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objOtpPage,Utilities.getTextVal(MLWalletShopItemsPage.objOtpPage, "Pop up"));
		Thread.sleep(2000);
		Utilities.click(MLWalletShopItemsPage.objOtpTextField, "Otp Text Field");
		Utilities.handleOtp(prop.getproperty("incorrectOtp"));
		Utilities.click(MLWalletShopItemsPage.objValidateBtn, "Validate Button");
		// Code to be written to validate incorrect otp msg
	}

	public void mlWallet_ShopItems_without_Input_Otp() throws Exception {
		ExtentReporter.HeaderChildNode("mlWallet_ShopItems_without_Input_Otp");
		mlWalletLogin(prop.getproperty("Branch_Verified"));
		mlWallet_ShopItems_Generic_Steps();
		Utilities.verifyElementPresent(MLWalletShopItemsPage.objOtpPage,Utilities.getTextVal(MLWalletShopItemsPage.objOtpPage, "Pop up"));
		Thread.sleep(2000);
		Utilities.click(MLWalletShopItemsPage.objValidateBtn, "Validate Button");
		String oOpsMsg = Utilities.getText(MLWalletShopItemsPage.objInvalidOtpPopUp);
		String supplyFieldsMsg = Utilities.getText(MLWalletShopItemsPage.objInvalidOtpPopUpMsg);
		logger.info(oOpsMsg + " " + supplyFieldsMsg + " Pop Up Message is displayed");
		ExtentReporter.extentLogger("", oOpsMsg + " " + supplyFieldsMsg + " Pop Up Message is displayed");

		logger.info("MLS_TC_04, Oops... Please supply all fields. - Error message is validated");
		ExtentReporter.extentLoggerPass("MLS_TC_04", "MLS_TC_04, Oops... Please supply all fields. - Error message is validated");
		Utilities.setScreenshotSource();
		System.out.println("-----------------------------------------------------------");
		Utilities.click(MLWalletShopItemsPage.objOkBtn, "OK Button");
		Utilities.click(MLWalletShopItemsPage.objCanceBtn, "Cancel Button");
		Utilities.click(MLWalletShopItemsPage.objBackArrowBtn, "Back Arrow Button");
		if (Utilities.verifyElementPresent(MLWalletShopItemsPage.objAvailableBalance, "Available Balance")) {
			logger.info("Navigated to Home Page");
			ExtentReporter.extentLogger("", "Navigated to Home Page");
		} else {
			logger.info("Failed to Navigate Home Page");
			ExtentReporter.extentLogger("", "Failed to Navigate Home Page");
		}
		mlWalletLogout();

	}





}

