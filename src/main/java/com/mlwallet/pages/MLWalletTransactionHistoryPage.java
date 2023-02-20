package com.mlwallet.pages;

import org.openqa.selenium.By;

public class MLWalletTransactionHistoryPage {
	
	public static By objRecentTransaction=By.xpath("//*[@text='Recent Transactions']");
	public static By objSeeMoreBtn=By.xpath("//*[@text='See More']");
	
	public static By objBillsPayTab=By.xpath("//*[@text='Billspay']");
	public static By objeLoadTab=By.xpath("//*[@text='eLoad']");
	public static By objSendMoneyTab=By.xpath("//*[@text='Send Money']");
	public static By objCashInTab=By.xpath("//*[@text='Cash In']");
	public static By objCashOutTab=By.xpath("//*[@text='Cash Out']");
	public static By objReceiveMoneyTab=By.xpath("//*[@text='Receive Money']");
	public static By objBalanceAdjustmentTab=By.xpath("//*[@text='Balance Adjustment']");
	public static By objMlShopTab=By.xpath("//*[@text='ML Shop']");
//	public static By objPayBillsHistory=By.xpath("//*[@text='Pay Bills' or @text='No Recent Transaction']");
	//public static By objPayBillsTransctionList=By.xpath("//*[@text='Pay Bills']/(following-sibling::android.widget.TextView)[2]");
	
	
	
	public static By objPayBillsTransctionList1(String moduleName)
	{
	   return	By.xpath("//*[@text='"+moduleName+"']/(following-sibling::android.widget.TextView)[2]");
	}
	
	public static By objBillHistory(String billModule,String transaction)
	{
		return By.xpath("//*[@text='"+billModule+"' or @text='"+transaction+"']");
	}

}
