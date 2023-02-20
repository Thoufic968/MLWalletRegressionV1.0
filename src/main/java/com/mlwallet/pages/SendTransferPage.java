package com.mlwallet.pages;

import org.openqa.selenium.By;

public class SendTransferPage {

    public static By objSendTransferBtn = By.xpath("//*[@text='Send /\n" +
            "Transfer']");

    public static By objSendMoney = By.xpath("//*[@text='Send Money']");

    public static By objToAnyMLBranch = By.xpath("//*[@text='To any ML Branch']");

    public static By objFirstname = By.xpath("//*[android.view.ViewGroup]/descendant::android.widget.EditText[@text='First Name']");

//    public static By objMiddleName = By.xpath("(//*[@class='android.widget.ImageView']/parent::android.view.ViewGroup/parent::android.view.ViewGroup/following-sibling::android.widget.EditText)[1]");

    public static By objMiddleName = By.xpath("//*[@text='Middle Name']");

    public static By objLastName = By.xpath("(//*[@class='android.widget.ImageView']/parent::android.view.ViewGroup/parent::android.view.ViewGroup/following-sibling::android.widget.EditText)[2]");

    public static By objMobileNumber = By.xpath("//*[@text='+63']/following-sibling::android.widget.EditText");

    public static By objCheckBox = By.xpath("//*[@class='android.widget.ImageView']");

    public static By objNextBtn = By.xpath("//*[@text='Next']");

    public static By objSavedRecipients = By.xpath("//*[@text='Saved Recipients']");

    public static By objKwartaPadala = By.xpath("//*[@text='Kwarta Padala']");

    public static By objSelectPaymentMethod = By.xpath("//*[@text='Select Payment Method']");

    public static By objMLWalletBalance = By.xpath("//*[@text='ML Wallet balance']");

    public static By objAmountTxtField = By.xpath("//*[@class='android.widget.EditText']");

    public static By objConfirmDetails = By.xpath("//*[@text='Confirm Details']");

    public static By objConfirmBtn = By.xpath("//*[@text='Confirm']");

    public static By objLocationPermission = By.xpath("//*[@text='WHILE USING THE APP']");

    public static By objSendMoneySuccessful = By.xpath("//*[@text='Send Money Successful']");

    public static By objPHPAmount = By.xpath("(//*[@text='Send Money - Kwarta Padala Successful']/following-sibling::android.widget.TextView)[1]");

    public static By objDate = By.xpath("(//*[@text='Send Money - Kwarta Padala Successful']/following-sibling::android.widget.TextView)[2]");

    public static By objReferenceNumber = By.xpath("(//*[@text='Send Money - Kwarta Padala Successful']/following-sibling::android.widget.TextView)[4]");

    public static By objReferenceNumberInTransactionDetails = By.xpath("(//*[@resource-id='Reference Number'])[2]");
    public static By objBackToHomeBtn = By.xpath("//*[@text='Back To Home']");

    public static By objSelectRecipient = By.xpath("//*[@text='Select Recipient']");

    public static By objSearchRecipient = By.xpath("//*[@class='android.widget.EditText']");

    public static By objAddRecipient = By.xpath("//*[@text='Add Recipient']");

    public static By objNickName = By.xpath("//*[@text='Nickname']");

    public static  By ObjSaveRecipient = By.xpath("//*[@text='Save Recipient']");

    public static By objSelectLastName(String sLastName,String sFirstName){
        return By.xpath("//*[contains(@text,'"+sLastName+", "+sFirstName+"')]");
    }

    public static By objBackArrow = By.xpath("//*[@class='android.widget.TextView' and ./parent::*[@class='android.view.ViewGroup' and ./parent::*[./parent::*[(./preceding-sibling::* | ./following-sibling::*)[@class='android.view.ViewGroup']]] and (./preceding-sibling::* | ./following-sibling::*)[@text]]]");

    public static By objDeleteRecipient = By.xpath("//*[@text='Delete']");

    public static By objEditRecipient = By.xpath("//*[@text='Edit']");

    public static By objPopupMsg = By.xpath("//*[@resource-id='something']/following-sibling::android.widget.TextView");

    public static By objRemoveBtn = By.xpath("//*[@text='Remove']");

    public static By objContactAlreadyExistMsg = By.xpath("//*[@text='Contact already exists.']");

    public static By objOkBtn = By.xpath("//*[@text='Ok']");

    public static By objEditRecipientLastName = By.xpath("(//*[@text='Middle Name']/following-sibling::android.widget.EditText)[1]");

    public static By objFirstNameRequiredMsg = By.xpath("//*[@text='First name is required']");

    public static By objMiddleNameRequiredMsg = By.xpath("//*[@text='Middle name is required']");

    public static By objLastNameRequiredMsg = By.xpath("//*[@text='Last name is required']");

    public static By objMobileNumberRequiredMsg = By.xpath("//*[@text='Mobile number is required']");

    public static By objFirstNameErrorMsg = By.xpath("//*[@text='First name must only contain letters and spaces']");

    public static By objMiddleNameErrorMsg = By.xpath("//*[@text='Middle name must only contain letters and spaces']");

    public static By objLastNameErrorMsg = By.xpath("//*[@text='Last name must only contain letters and spaces']");

    public static By objMobileNumberErrorMsg = By.xpath("//*[@text='Mobile number is invalid']");

    public static By objReferenceNumberInTransaction = By.xpath("//*[contains(@text,'KPTN')]");

    public static By objInvalidAmountMsg = By.xpath("//*[@text='PHP']/parent::android.view.ViewGroup/following-sibling::android.widget.TextView");

    public static By objInsufficientAmountMsg = By.xpath("(//*[@resource-id='modal-confirm-button']/preceding-sibling::android.widget.TextView)[2]");

    public static By objMaxLimitErrorMsg = By.xpath("(//*[@resource-id='modal-confirm-button']/preceding-sibling::android.widget.TextView)[2]");

//======================================== Send to Wallet User ===================================//

    public static By objToAMLWalletUser = By.xpath("//*[@text='To a ML Wallet user']");

    public static By objMobileNumberField = By.xpath("//*[@text='Mobile Number']/following-sibling::android.widget.EditText");

    public static By objSendPHPBtn = By.xpath("//*[contains(@text,'Send Php')]");

    public static By objSendMoneyMLWallet = By.xpath("//*[@text='Send Money - ML Wallet Successful']");

    public static By objSendMoneyMLWalletDate = By.xpath("(//*[@text='Send Money - ML Wallet Successful']/following-sibling::android.widget.TextView)[2]");

    public static By objSendMoneyMLWalletPHP = By.xpath("(//*[@text='Send Money - ML Wallet Successful']/following-sibling::android.widget.TextView)[1]");

    public static By objMLWalletReferenceNumber = By.xpath("(//*[@resource-id='Transaction No.'])[2]");

    public static By objViewAllBtn = By.xpath("//*[@text='View All']");

    public static By objEllipsisBtn = By.xpath("((//*[@class='android.view.ViewGroup' and ./parent::*[@class='android.widget.ScrollView']]/*/*[@class='android.view.ViewGroup' and ./parent::*[@class='android.view.ViewGroup']])[1]/*/*/*[@class='android.widget.TextView' and ./parent::*[@class='android.view.ViewGroup']])[3]");

    public static By objDeleteBtn = By.xpath("//*[@text='Delete']");

    public static By objSaveToMyFavorite = By.xpath("//*[@text='Save to my favorite']");

    public static By objAddedToFavoritesMsg = By.xpath("//*[@text='Successfully Added to Favorites']");

    public static By objSelectFavorite = By.xpath("(//*[@class='android.widget.HorizontalScrollView']/child::android.view.ViewGroup/child::android.view.ViewGroup/child::android.view.ViewGroup/child::android.widget.TextView)[1]");

    public static By objUnRegisteredMobNumber = By.xpath("//*[@text='Receiver not Found!']");

    public static By objFavRemovedMsg = By.xpath("//*[@text='Successfully Removed']");


}
