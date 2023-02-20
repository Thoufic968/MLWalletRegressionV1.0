package com.mlwallet.pages;

import org.openqa.selenium.By;

public class MLWalletCashOutPage {

    public static By objCashOut = By.xpath("//*[@text='Cashout /\n" +
            "Withdraw']");
//=========================================== ML Branch ========================================================//
    public static By objToAnyMLBranch = By.xpath("//*[@text='To any ML Branch']");

    public static By objCashOutToBranch = By.xpath("//*[@text='Cash Out To Branch']");

    public static By objAmountField = By.xpath("//*[@class='android.widget.EditText']");

    public static By objNextBtn = By.xpath("//*[@text='Next']");

    public static By objContinueBtn = By.xpath("//*[@text='Continue']");

    public static By objLocationPermission = By.xpath("//*[@text='WHILE USING THE APP']");

    public static By objBackToHomeBtn = By.xpath("//*[@text='Back To Home']");

    public static By objCreatedDate = By.xpath("//*[contains(@text,'Created')]");

    public static By objReferenceNumber = By.xpath("//*[contains(@text,'KPTN')]");

    public static By objReferenceNumberInCashOut = By.xpath("(//*[@resource-id='Reference Number'])[2]");

    public static By objTransactionDetails = By.xpath("(//*[@text='Transaction Details'])");

    public static By objBackArrowBtn = By.xpath("//*[@text='Transaction Details']/preceding-sibling::android.view.ViewGroup/child::android.widget.TextView");

    public static By objMaxLimitTxt = By.xpath("(//*[@resource-id='modal-confirm-button']/preceding-sibling::android.widget.TextView)[2]");

    public static By objOkBtn = By.xpath("//*[@resource-id='modal-confirm-button']");

    public static By objCashOutPageBackArrowBtn = By.xpath("//*[@text='Cash Out']/preceding-sibling::android.view.ViewGroup/child::android.widget.TextView");

    public static By objCashOutOptionsBackArrowBtn = By.xpath("//*[@text='Cash out / Withdraw']/preceding-sibling::android.view.ViewGroup/child::android.widget.TextView");

//============================================ ML Bank ===================================================================//

    public static By objToAnyBank = By.xpath("//*[@text='To Any Bank']");

    public static By BogusBank = By.xpath("//*[@text='BogusBank']");

    public static By objBankInformation = By.xpath("//*[@text='Bank Information']");

    public static By objAccountNumberField = By.xpath("//*[@text='Account Number']");

    public static By objFirstname = By.xpath("//*[@text='Account Holder First Name']");

    public static By objMiddleName = By.xpath("//*[@text='Account Holder Middle Name']");

    public static By objLastName = By.xpath("//*[@text='Account Holder Last Name']");

    public static By objEmailAddress = By.xpath("//*[@text='Account Holder Email Address']");

    public static By objCheckBox = By.xpath("//*[@class='android.widget.ImageView']");

    public static By objConfirmBtn = By.xpath("//*[@text='Confirm']");

    public static By objTransactionNo = By.xpath("//*[@text='Transaction No.']/following-sibling::android.widget.TextView");

  public static By objTransactionReferenceNo = By.xpath("");

    public static By objTransactionSuccessMessage = By.xpath("//*[@text='Transaction Successful']");


    public static By objTransactionReceipt = By.xpath("//*[@text='Transaction Receipt']");

    public static By objAccInvalidErrorMsg = By.xpath("(//*[@resource-id='modal-confirm-button']/preceding-sibling::android.widget.TextView)[2]");

    public static By objBackArrow = By.xpath("//*[@class='android.widget.TextView' and ./parent::*[@class='android.view.ViewGroup' and ./parent::*[@class='android.view.ViewGroup' and ./parent::*[./parent::*[@class='android.view.ViewGroup'] and (./preceding-sibling::* | ./following-sibling::*)[./*[./*[@class='android.widget.ScrollView']]]]]]]");

    public static By objBankListBackArrow = By.xpath("//*[@text='Bank List']/preceding-sibling::android.view.ViewGroup/child::android.widget.TextView");

    public static By objDragonPayPopUpMsg = By.xpath("//*[@resource-id='something']/following-sibling::android.widget.TextView");

    public static By objBankMaxLimitTxt = By.xpath("//*[@resource-id='something']/following-sibling::android.widget.TextView");

    public static By objMinimumTransactionErrorMsg = By.xpath("(//*[@resource-id='modal-confirm-button']/preceding-sibling::android.widget.TextView)[2]");

//    public static By objReceiversName = By.xpath("//*[@text='Review Transaction']");
}
