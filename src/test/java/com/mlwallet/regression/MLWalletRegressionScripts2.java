package com.mlwallet.regression;

import com.utility.Utilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.business.mlwallet.MLWalletBusinessLogic;

public class MLWalletRegressionScripts2 {

    public  MLWalletBusinessLogic MLWalletBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeMethod
    //@BeforeSuite
    public void beforeMethod(String deviceName,String portno) throws Exception {
        Utilities.relaunch = true;
        MLWalletBusinessLogic = new MLWalletBusinessLogic("MLWallet",deviceName,portno);
    }


    @Test(priority = 1)
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
	@Test(priority = 2)
	public void mlWalletTransactionHistory_Scenario() throws Exception
	{
		MLWalletBusinessLogic.mlWallet_TransactionHistory();
	}

	@Test(priority = 3)
	public void mlWalletShopItems_Scenario() throws Exception
	{
		MLWalletBusinessLogic.mlWallet_ShopItems_without_Input_Otp();
		MLWalletBusinessLogic.mlWallet_ShopItems_with_Insufficient_Balance();
	}





}
