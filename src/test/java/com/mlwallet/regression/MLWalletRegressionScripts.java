package com.mlwallet.regression;


import com.driverInstance.DriverManager;
import com.utility.Utilities;
import org.testng.annotations.*;
import com.business.mlwallet.MLWalletBusinessLogic;


public class MLWalletRegressionScripts {

	public  MLWalletBusinessLogic MLWalletBusinessLogic;
	
	
	//@BeforeSuite(groups = { "All" })
	 @Parameters({"deviceName","portno"})
	 @BeforeMethod
	public void before(String deviceName,String portno) throws Exception {
		 Utilities.relaunch = true;
		 MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
	 }


	@Test(priority = 0)
	public void mlWalletLoginScenario() throws Exception
	{
		MLWalletBusinessLogic.LogInScenarioWithValidMobNumber_Lgn_TC_01();
		MLWalletBusinessLogic.LogInScenarioWithInvalidMobNumber();
		MLWalletBusinessLogic.LogInScenarioWithValidOTP();
	}

	@Test(priority = 1)
	public void mlWalletCashOutWithdraw() throws Exception
	{
		MLWalletBusinessLogic.cashOutWithdrawBank("100");
		MLWalletBusinessLogic.cashOutWithInvalidAccNumber();
		MLWalletBusinessLogic.cashOutWithdrawBankMaxAmount("60000");
		MLWalletBusinessLogic.cashOutWithdrawMinTransactionLimit("10");
		MLWalletBusinessLogic.cashOutWithdrawBranch("10");
		MLWalletBusinessLogic.cashOutMaxLimit("40000");
		MLWalletBusinessLogic.cashOutBuyerTierLevelAcc("100");
	}


	@Test(priority = 2)
	public void mlWalletSendMoneyToMLBranch() throws Exception
	{
		MLWalletBusinessLogic.sendMoneyToMLBranch("100");
//		MLWalletBusinessLogic.sendMoneyRequiredDetails();
//		MLWalletBusinessLogic.sendMoneyInvalidDetails();
		MLWalletBusinessLogic.sendMoneyAddRecipient();
		MLWalletBusinessLogic.sendMoneyContactDuplicate();
		MLWalletBusinessLogic.sendMoneyToSavedRecipient("100");
		MLWalletBusinessLogic.sendMoneyEditRecipient();
		MLWalletBusinessLogic.sendMoneyDeleteRecipient();
		MLWalletBusinessLogic.sendMoneyInvalidAmount("0");
		MLWalletBusinessLogic.sendMoneyInsufficientAmount("10000");
		MLWalletBusinessLogic.sendMoneyMaximumAmount("100000");
	}


	@Test(priority = 3)
	public void mlWalletSendMoneyToWallet() throws Exception
	{
		MLWalletBusinessLogic.sendToMLWalletUser("10");
		MLWalletBusinessLogic.sendMoneyMLWalletToExistingReceiver("10");
		MLWalletBusinessLogic.sendToMLWalletInvalidMobNumber();
		MLWalletBusinessLogic.sendToMLWalletUnRegisteredNumber();
		MLWalletBusinessLogic.sendToMLWalletInvalidAmount("0");
		MLWalletBusinessLogic.sendToMLWalletInsufficientAmount("30000");
		MLWalletBusinessLogic.sendMoneyMLWalletMaximumAmount("100000");
		MLWalletBusinessLogic.sendMoneyDeleteFromFavorites();

	}
	@Test(priority = 4)
	public void mlWalletTransactionHistory_Scenario() throws Exception
	{
		MLWalletBusinessLogic.mlWallet_TransactionHistory();
	}

	@Test(priority = 5)
	public void mlWalletShopItems_Scenario() throws Exception
	{
		MLWalletBusinessLogic.mlWallet_ShopItems_without_Input_Otp();
		MLWalletBusinessLogic.mlWallet_ShopItems_with_Insufficient_Balance();
	}









//	@Test
//	public void mlWalletLoginScenario1() throws Exception
//	{
//		MLWalletrBusinessLogic.mlWalletLoginScenario1();
//	}
	
	


//	  @AfterMethod
//	  public synchronized void tearDown() {
//		 MLWalletBusinessLogic.tearDown();
//
//	 }


}