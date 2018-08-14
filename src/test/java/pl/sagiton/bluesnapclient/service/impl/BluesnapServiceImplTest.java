package pl.sagiton.bluesnapclient.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sagiton.bluesnapclient.model.cardtransaction.CardHolder;
import pl.sagiton.bluesnapclient.model.cardtransaction.CardTransaction;
import pl.sagiton.bluesnapclient.model.cardtransaction.CreditCard;
import pl.sagiton.bluesnapclient.model.vaultedshopper.CreditCardInfo;
import pl.sagiton.bluesnapclient.model.vaultedshopper.PaymentSources;
import pl.sagiton.bluesnapclient.model.vaultedshopper.VaultedShopper;
import pl.sagiton.bluesnapclient.model.vendors.*;
import pl.sagiton.bluesnapclient.service.exceptions.BluesnapException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BluesnapServiceImplTest {
    @Autowired
    private BluesnapServiceImpl bluesnapServiceImpl;
    private static final String VENDOR_ID = "10400732";
    private static final String SHOPPER_ID = "22810565";
    private static final String NEW_LAST_NAME = "NewLastName";
    private static final String NEW_FIRST_NAME = "NewName";
    private static final String NEW_ADDRESS = "new test address";
    private static final String VISA_TYPE = "VISA";

    private static final String BLUESNAP_ROOT_URI = "https://sandbox.bluesnap.com/services/2";
    private static final String BLUESNAP_USERNAME = "API_153192791914019503127";
    private static final String BLUESNAP_PASSWORD = "LajkonikTravel12#";

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        bluesnapServiceImpl = new BluesnapServiceImpl(BLUESNAP_ROOT_URI, BLUESNAP_USERNAME, BLUESNAP_PASSWORD);
    }

    @Test
    public void shouldCreateNewVendor() {
        Vendor vendor = buildVendor();
        URI response = bluesnapServiceImpl.createVendor(vendor);
        assertNotNull(response);
    }

    @Test
    public void shouldUpdateVendor() {
        Vendor vendor = bluesnapServiceImpl.getVendor(VENDOR_ID);
        assertNotNull(vendor);

        String vendorResponseId = vendor.getVendorId().toString();
        assertEquals(VENDOR_ID, vendorResponseId);

        vendor.setLastName(NEW_LAST_NAME);
        vendor.getVendorPrincipal().setFirstName(NEW_FIRST_NAME);
        List<PayoutInfo> payoutInfo = vendor.getPayoutInfo();
        payoutInfo.get(0).setAddress(NEW_ADDRESS);
        vendor.setPayoutInfo(payoutInfo);
        VendorPrincipal vendorPrincipalDataToUpdate = vendor.getVendorPrincipal();

        bluesnapServiceImpl.updateVendor(VENDOR_ID, vendor);

        Vendor vendorAfterUpdate = bluesnapServiceImpl.getVendor(VENDOR_ID);
        VendorPrincipal vendorPrincipalAfterUpdate = vendorAfterUpdate.getVendorPrincipal();
        String vendorLastNameAfterUpdate = vendorAfterUpdate.getLastName();
        assertEquals(NEW_LAST_NAME, vendorLastNameAfterUpdate);
        assertEquals(vendorPrincipalDataToUpdate, vendorPrincipalAfterUpdate);
    }

    @Test
    public void shouldUpdateVendorPayoutInfo() {
        Vendor vendor = bluesnapServiceImpl.getVendor(VENDOR_ID);

        PayoutInfo updatedPayoutInfo = buildPayoutInfo();
        List<PayoutInfo> payoutInfoList = new ArrayList<>();
        payoutInfoList.add(updatedPayoutInfo);
        vendor.setPayoutInfo(payoutInfoList);

        bluesnapServiceImpl.updateVendor(VENDOR_ID, vendor);

        Vendor vendorAfterUpdate = bluesnapServiceImpl.getVendor(VENDOR_ID);
        assertEquals(vendor.getPayoutInfo(), vendorAfterUpdate.getPayoutInfo());
    }

    @Test
    public void shouldGetVendor() {
        Vendor vendor = bluesnapServiceImpl.getVendor(VENDOR_ID);
        assertNotNull(vendor);
        String vendorIdFromResponse = vendor.getVendorId().toString();
        assertEquals(VENDOR_ID, vendorIdFromResponse);

    }

    @Test
    public void shouldGetVendors() {
        VendorList vendorList = bluesnapServiceImpl.getVendors();

        assertNotNull(vendorList);
        List<Vendor> vendorResponseList = vendorList.getVendor();

        assertNotNull(vendorResponseList);
        assertFalse(vendorList.getVendor().isEmpty());

        Vendor vendorFromResponse = vendorList.getVendor().get(0);

        assertNotNull(vendorFromResponse);

    }

    @Test
    public void shouldProcureTransactionBetweenShopperAndVendor() {
        CardTransaction cardTransaction = buildVaultedShopperMerchantTransaction();
        CardTransaction cardTransactionResponse = bluesnapServiceImpl.pay(cardTransaction);

        assertNotNull(cardTransactionResponse);

        Double amountFromRequest = cardTransaction.getAmount();
        Double amountFromResponse = cardTransactionResponse.getAmount();

        CreditCard creditCardFromRequest = cardTransaction.getCreditCard();
        CreditCard creditCardFromResponse = cardTransactionResponse.getCreditCard();

        String cardLastFourDigitsFromRequest = creditCardFromRequest.getCardLastFourDigits();
        String cardLastFourDigitsFromResponse = creditCardFromResponse.getCardLastFourDigits();

        VendorsInfo vendorsInfoFromRequest = cardTransaction.getVendorsInfo();
        VendorsInfo vendorsInfoFromResponse = cardTransactionResponse.getVendorsInfo();

        List<VendorInfo> vendorInfoListFromRequest = vendorsInfoFromRequest.getVendorInfo();
        List<VendorInfo> vendorInfoListFromResponse = vendorsInfoFromResponse.getVendorInfo();

        VendorInfo vendorInfoFromRequest = vendorInfoListFromRequest.get(0);
        VendorInfo vendorInfoFromResponse = vendorInfoListFromResponse.get(0);

        Long vendorIdFromRequest = vendorInfoFromRequest.getVendorId();
        Long vendorIdFromResponse = vendorInfoFromResponse.getVendorId();

        Integer vaultedShopperIdFromRequest = cardTransaction.getVaultedShopperId();
        Integer vaultedShopperIdFromResponse = cardTransactionResponse.getVaultedShopperId();

        String currencyFromRequest = cardTransaction.getCurrency();
        String currencyFromResponse = cardTransaction.getCurrency();

        assertEquals(amountFromRequest, amountFromResponse);
        assertEquals(cardLastFourDigitsFromRequest, cardLastFourDigitsFromResponse);
        assertEquals(vaultedShopperIdFromRequest, vaultedShopperIdFromResponse);
        assertEquals(vendorIdFromRequest, vendorIdFromResponse);
        assertEquals(currencyFromRequest, currencyFromResponse);

    }

    @Test
    public void shouldProcureTransactionBetweenCardAndVendor() {
        CardTransaction cardTransaction = buildCardTransaction();
        CardTransaction cardTransactionResponse = bluesnapServiceImpl.pay(cardTransaction);

        assertNotNull(cardTransactionResponse);

        Double amountFromRequest = cardTransaction.getAmount();
        Double amountFromResponse = cardTransactionResponse.getAmount();

        CreditCard creditCardFromRequest = cardTransaction.getCreditCard();
        CreditCard creditCardFromResponse = cardTransactionResponse.getCreditCard();

        String cardLastFourDigitsFromRequest = creditCardFromRequest.getCardLastFourDigits();
        String cardLastFourDigitsFromResponse = creditCardFromResponse.getCardLastFourDigits();

        VendorsInfo vendorsInfoFromRequest = cardTransaction.getVendorsInfo();
        VendorsInfo vendorsInfoFromResponse = cardTransactionResponse.getVendorsInfo();

        List<VendorInfo> vendorInfoListFromRequest = vendorsInfoFromRequest.getVendorInfo();
        List<VendorInfo> vendorInfoListFromResponse = vendorsInfoFromResponse.getVendorInfo();

        VendorInfo vendorInfoFromRequest = vendorInfoListFromRequest.get(0);
        VendorInfo vendorInfoFromResponse = vendorInfoListFromResponse.get(0);

        Long vendorIdFromRequest = vendorInfoFromRequest.getVendorId();
        Long vendorIdFromResponse = vendorInfoFromResponse.getVendorId();

        String currencyFromRequest = cardTransaction.getCurrency();
        String currencyFromResponse = cardTransaction.getCurrency();

        assertEquals(amountFromRequest, amountFromResponse);
        assertEquals(cardLastFourDigitsFromRequest, cardLastFourDigitsFromResponse);
        assertEquals(vendorIdFromRequest, vendorIdFromResponse);
        assertEquals(currencyFromRequest, currencyFromResponse);

    }

    @Test
    public void shouldCreateVaultedShopper() {
        VaultedShopper vaultedShopper = buildVaultedShopper();
        VaultedShopper vaultedShopperResponse = bluesnapServiceImpl.createVaultedShopper(vaultedShopper);

        assertNotNull(vaultedShopperResponse.getVaultedShopperId());

        String vaultedShopperRequestFirstName = vaultedShopper.getFirstName();
        String vaultedShopperResponseFirstName = vaultedShopperResponse.getFirstName();

        String vaultedShopperRequestLastName = vaultedShopper.getLastName();
        String vaultedShopperResponseLastName = vaultedShopperResponse.getLastName();

        PaymentSources paymentSourcesRequest = vaultedShopper.getPaymentSources();
        PaymentSources paymentSourcesResponse = vaultedShopperResponse.getPaymentSources();

        List<CreditCardInfo> creditCardInfoListRequest = paymentSourcesRequest.getCreditCardInfo();
        List<CreditCardInfo> creditCardInfoListResponse = paymentSourcesResponse.getCreditCardInfo();

        CreditCardInfo creditCardInfoRequest = creditCardInfoListRequest.get(0);
        CreditCardInfo creditCardInfoResponse = creditCardInfoListResponse.get(0);

        CreditCard creditCardRequest = creditCardInfoRequest.getCreditCard();
        CreditCard creditCardResponse = creditCardInfoResponse.getCreditCard();

        String creditCardLastFourDigitsRequest = creditCardRequest.getCardLastFourDigits();
        String creditCardLastFourDigitsResponse = creditCardResponse.getCardLastFourDigits();

        String creditCardResponseType = creditCardResponse.getCardType();

        Integer creditCardExpirationMonthRequest = creditCardRequest.getExpirationMonth();
        Integer creditCardExpirationMonthResponse = creditCardResponse.getExpirationMonth();

        Integer creditCardExpirationYearRequest = creditCardRequest.getExpirationYear();
        Integer creditCardExpirationYearResponse = creditCardResponse.getExpirationYear();

        assertEquals(vaultedShopperRequestFirstName, vaultedShopperResponseFirstName);
        assertEquals(vaultedShopperRequestLastName, vaultedShopperResponseLastName);
        assertEquals(creditCardLastFourDigitsRequest, creditCardLastFourDigitsResponse);
        assertEquals(VISA_TYPE, creditCardResponseType);
        assertEquals(creditCardExpirationMonthRequest, creditCardExpirationMonthResponse);
        assertEquals(creditCardExpirationYearRequest, creditCardExpirationYearResponse);
    }

    @Test
    public void shouldUpdateVaultedShopper() {
        VaultedShopper vaultedShopper = bluesnapServiceImpl.getVaultedShopper(SHOPPER_ID);
        vaultedShopper.setLastName(NEW_LAST_NAME);

        bluesnapServiceImpl.updateVaultedShopper(SHOPPER_ID, vaultedShopper);
        VaultedShopper vaultedShopperResponse = bluesnapServiceImpl.getVaultedShopper(SHOPPER_ID);

        assertNotNull(vaultedShopperResponse.getVaultedShopperId());

        String vaultedShopperIdResponse = vaultedShopperResponse.getVaultedShopperId().toString();

        assertEquals(SHOPPER_ID, vaultedShopperIdResponse);

        String vaultedShopperRequestFirstName = vaultedShopper.getFirstName();
        String vaultedShopperResponseFirstName = vaultedShopperResponse.getFirstName();

        String vaultedShopperRequestLastName = vaultedShopper.getLastName();
        String vaultedShopperResponseLastName = vaultedShopperResponse.getLastName();

        assertEquals(vaultedShopperRequestFirstName, vaultedShopperResponseFirstName);
        assertEquals(vaultedShopperRequestLastName, vaultedShopperResponseLastName);
    }

    @Test
    public void shouldGetVaultedShopper() {
        VaultedShopper vaultedShopper = bluesnapServiceImpl.getVaultedShopper(SHOPPER_ID);
        vaultedShopper.setLastName(NEW_LAST_NAME);
        assertNotNull(vaultedShopper.getVaultedShopperId());

        String vaultedShopperIdResponse = vaultedShopper.getVaultedShopperId().toString();

        assertEquals(SHOPPER_ID, vaultedShopperIdResponse);

        assertNotNull(vaultedShopper.getFirstName());
        assertNotNull(vaultedShopper.getLastName());
    }

    @Test
    public void shouldAddAnotherCardToVaultedShopper() {
        VaultedShopper vaultedShopper = bluesnapServiceImpl.getVaultedShopper(SHOPPER_ID);
        CreditCardInfo creditCardInfo = buildCreditCardInfo2();
        PaymentSources paymentSources = vaultedShopper.getPaymentSources();
        List<CreditCardInfo> creditCardInfoList = paymentSources.getCreditCardInfo();
        creditCardInfoList.add(creditCardInfo);
        paymentSources.setCreditCardInfo(creditCardInfoList);
        vaultedShopper.setPaymentSources(paymentSources);
        bluesnapServiceImpl.updateVaultedShopper(SHOPPER_ID, vaultedShopper);
        VaultedShopper vaultedShopperResponse = bluesnapServiceImpl.getVaultedShopper(SHOPPER_ID);
        assertNotNull(vaultedShopper.getVaultedShopperId());

        assertEquals(SHOPPER_ID, vaultedShopper.getVaultedShopperId().toString());
        assertEquals(6, vaultedShopperResponse.getPaymentSources().getCreditCardInfo().size());
        Integer size = vaultedShopperResponse.getPaymentSources().getCreditCardInfo().size();
        size--;

        PaymentSources paymentSourcesRequest = vaultedShopper.getPaymentSources();
        PaymentSources paymentSourcesResponse = vaultedShopperResponse.getPaymentSources();

        List<CreditCardInfo> creditCardInfoListRequest = paymentSourcesRequest.getCreditCardInfo();
        List<CreditCardInfo> creditCardInfoListResponse = paymentSourcesResponse.getCreditCardInfo();

        CreditCardInfo creditCardInfoRequest = creditCardInfoListRequest.get(size);
        CreditCardInfo creditCardInfoResponse = creditCardInfoListResponse.get(size);

        CreditCard creditCardRequest = creditCardInfoRequest.getCreditCard();
        CreditCard creditCardResponse = creditCardInfoResponse.getCreditCard();

        String creditCardLastFourDigitsRequest = creditCardRequest.getCardLastFourDigits();
        String creditCardLastFourDigitsResponse = creditCardResponse.getCardLastFourDigits();

        String creditCardResponseType = creditCardResponse.getCardType();

        Integer creditCardExpirationMonthRequest = creditCardRequest.getExpirationMonth();
        Integer creditCardExpirationMonthResponse = creditCardResponse.getExpirationMonth();

        Integer creditCardExpirationYearRequest = creditCardRequest.getExpirationYear();
        Integer creditCardExpirationYearResponse = creditCardResponse.getExpirationYear();

        assertEquals(creditCardLastFourDigitsRequest, creditCardLastFourDigitsResponse);
        assertEquals(VISA_TYPE, creditCardResponseType);
        assertEquals(creditCardExpirationMonthRequest, creditCardExpirationMonthResponse);
        assertEquals(creditCardExpirationYearRequest, creditCardExpirationYearResponse);
    }

    @Test
    public void shouldThrowExceptionInsteadOfGettingVendor() {
        assertThrows(BluesnapException.class, () -> bluesnapServiceImpl.getVendor(null));

    }

    @Test
    public void shouldThrowExceptionInsteadOfAddingVaultedShopper() {
        VaultedShopper vaultedShopper = buildBrokenVaultedShopper();
        assertThrows(BluesnapException.class, () -> bluesnapServiceImpl.createVaultedShopper(vaultedShopper));

    }

    @Test
    public void shouldThrowExceptionInsteadUpdatingVaultedShopper() {
        assertThrows(BluesnapException.class, () -> bluesnapServiceImpl.updateVaultedShopper(SHOPPER_ID, null));

    }

    @Test
    public void shouldThrowExceptionInsteadOfGettingVaultedShopper() {
        assertThrows(BluesnapException.class, () -> bluesnapServiceImpl.getVaultedShopper(null));

    }

    @Test
    public void shouldThrowExceptionInsteadOfAddingVendor() {
        Vendor vendor = buildBrokenVendor();
        assertThrows(BluesnapException.class, () -> bluesnapServiceImpl.createVendor(vendor));

    }

    @Test
    public void shouldThrowExceptionInsteadUpdatingVendor() {
        assertThrows(BluesnapException.class, () -> bluesnapServiceImpl.updateVendor(VENDOR_ID, null));

    }

    @Test
    public void shouldThrowExceptionInsteadOfPayment() {
        assertThrows(BluesnapException.class, () -> bluesnapServiceImpl.pay(null));

    }

    @Test
    public void shouldThrowRESTExceptionInsteadOfPayment() {
        CardTransaction cardTransaction = buildBrokenCardTransaction();
        assertThrows(BluesnapException.class, () -> bluesnapServiceImpl.pay(cardTransaction));

    }


    private CardTransaction buildCardTransaction() {
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setAmount(50.0);
        cardTransaction.setSoftDescriptor("DescTest");

        CardHolder cardHolder = new CardHolder();
        cardHolder.setFirstName("test1");
        cardHolder.setLastName("tester");
        cardHolder.setZip("53230");
        cardHolder.setCountry("PL");
        cardTransaction.setCardHolderInfo(cardHolder);

        cardTransaction.setCurrency("EUR");

        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber("4263982640269299");
        creditCard.setExpirationMonth(11);
        creditCard.setExpirationYear(2018);
        creditCard.setSecurityCode(111);
        creditCard.setCardLastFourDigits("9299");
        cardTransaction.setCreditCard(creditCard);
        cardTransaction.setCardTransactionType("AUTH_CAPTURE");

        VendorsInfo vendorsInfo = new VendorsInfo();
        List<VendorInfo> vendorsInfoList = new ArrayList<>();
        VendorInfo vendorInfo = new VendorInfo();
        vendorInfo.setVendorId(581181L);
        vendorsInfoList.add(vendorInfo);
        vendorsInfo.setVendorInfo(vendorsInfoList);
        cardTransaction.setVendorsInfo(vendorsInfo);
        return cardTransaction;
    }

    private CardTransaction buildBrokenCardTransaction() {
        CardTransaction cardTransaction = new CardTransaction();
        cardTransaction.setAmount(50.0);
        cardTransaction.setSoftDescriptor("DescTest");

        CardHolder cardHolder = new CardHolder();
        cardHolder.setFirstName("test1");
        cardHolder.setLastName("tester");
        cardHolder.setZip("53230");
        cardHolder.setCountry("PL");
        cardTransaction.setCardHolderInfo(cardHolder);

        cardTransaction.setCurrency("EUR");

        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber("4263982640269299");
        creditCard.setExpirationMonth(11);
        creditCard.setExpirationYear(2018);
        creditCard.setSecurityCode(111);
        creditCard.setCardLastFourDigits("9299");
        cardTransaction.setCreditCard(creditCard);
        // cardTransaction.setCardTransactionType("AUTH_CAPTURE");

        VendorsInfo vendorsInfo = new VendorsInfo();
        List<VendorInfo> vendorsInfoList = new ArrayList<>();
        VendorInfo vendorInfo = new VendorInfo();
        vendorInfo.setVendorId(581181L);
        vendorsInfoList.add(vendorInfo);
        vendorsInfo.setVendorInfo(vendorsInfoList);
        cardTransaction.setVendorsInfo(vendorsInfo);
        return cardTransaction;
    }

    private Vendor buildVendor() {
        //Mandatory
        Vendor vendor = new Vendor();
        vendor.setEmail("vendor1@example.com");
        vendor.setFirstName("Joe");
        vendor.setLastName("Smith");
        vendor.setPhone("+48592342543");
        vendor.setAddress("123 Test st.");
        vendor.setCity("Wroclaw");
        vendor.setCountry("PL");
        vendor.setZip("53-230");
        vendor.setDefaultPayoutCurrency("EUR");
        //Mandatory
        VendorPrincipal vendorPrincipal = new VendorPrincipal();
        vendorPrincipal.setFirstName("Joe");
        vendorPrincipal.setLastName("Smith");
        vendorPrincipal.setPhone("+4854629124");
        vendorPrincipal.setAddress("123 test st.");
        vendorPrincipal.setCity("Wroclaw");
        vendorPrincipal.setCountry("PL");
        vendorPrincipal.setZip("53-230");
        vendorPrincipal.setDob("28-09-1991");
        vendorPrincipal.setPersonalIdentificationNumber("1234");
        vendorPrincipal.setPassportNumber("0000177");
        vendorPrincipal.setEmail("test@test.pl");
        vendor.setVendorPrincipal(vendorPrincipal);
        ///???
        VendorAgreement vendorAgreement = new VendorAgreement();
        vendorAgreement.setCommissionPercent("30");
        vendor.setVendorAgreement(vendorAgreement);
        //Mandatory
        PayoutInfo payoutInfo = buildPayoutInfo();
        List<PayoutInfo> payoutInfoList = new ArrayList<>();
        payoutInfoList.add(payoutInfo);
        vendor.setPayoutInfo(payoutInfoList);
        return vendor;
    }

    private Vendor buildBrokenVendor() {
        //Mandatory
        Vendor vendor = new Vendor();
        vendor.setEmail("vendor1example.com");
        vendor.setFirstName("Joe");
        vendor.setLastName("Smith");
        vendor.setPhone("+48592342543");
        vendor.setAddress("123 Test st.");
        vendor.setCity("Wroclaw");
        vendor.setCountry("PL");
        vendor.setZip("53-230");
        vendor.setDefaultPayoutCurrency("EUR");
        VendorAgreement vendorAgreement = new VendorAgreement();
        vendorAgreement.setCommissionPercent("30");
        vendor.setVendorAgreement(vendorAgreement);
        return vendor;
    }

    private PayoutInfo buildPayoutInfo() {
        PayoutInfo payoutInfo = new PayoutInfo();
        payoutInfo.setPayoutType("SEPA"); //by us
        payoutInfo.setBaseCurrency("EUR"); //by us
        payoutInfo.setNameOnAccount("vendor"); // name of company
        payoutInfo.setBankAccountType("CHECKING");
        payoutInfo.setBankAccountClass("PERSONAL"); //company business
        payoutInfo.setBankName("Leumi"); //clientside
        payoutInfo.setBankId(12445); //empty or whatever?
        payoutInfo.setCountry("pl"); //clientside
        payoutInfo.setCity("wroclaw"); //clientside
        payoutInfo.setAddress("test 1 bank"); //clientside
        payoutInfo.setZip("51-300");//clientside
        payoutInfo.setBankAccountId(36628822); //vendor bank acc extract from iban
        payoutInfo.setIban("PL10105000997603123456789123"); //clientside
        payoutInfo.setSwiftBic("POLUPLPR"); //clientside
        payoutInfo.setMinimalPayoutAmount(50); //clients
        return payoutInfo;
    }

    private VaultedShopper buildVaultedShopper() {
        VaultedShopper vaultedShopper = new VaultedShopper();
        vaultedShopper.setEmail("vendor1@example.com");
        vaultedShopper.setFirstName("Joe");
        vaultedShopper.setLastName("Smith");
        vaultedShopper.setPhone("+48592342543");
        vaultedShopper.setAddress("123 Test st.");
        vaultedShopper.setCity("Wroclaw");
        vaultedShopper.setCountry("PL");
        vaultedShopper.setZip("53-230");
        //below exclusive for card management
        PaymentSources paymentSources = buildPaymentSources();
        vaultedShopper.setPaymentSources(paymentSources);
        return vaultedShopper;
    }

    private VaultedShopper buildBrokenVaultedShopper() {
        VaultedShopper vaultedShopper = new VaultedShopper();
        vaultedShopper.setEmail("vendor1example.com");
        vaultedShopper.setFirstName("Joe");
        vaultedShopper.setLastName("Smith");
        vaultedShopper.setPhone("+48592342543");
        vaultedShopper.setAddress("123 Test st.");
        vaultedShopper.setCity("Wroclaw");
        vaultedShopper.setCountry("PL");
        vaultedShopper.setZip("53-230");
        //below exclusive for card management
        PaymentSources paymentSources = buildPaymentSources();
        vaultedShopper.setPaymentSources(paymentSources);
        return vaultedShopper;
    }

    private PaymentSources buildPaymentSources() {
        PaymentSources paymentSources = new PaymentSources();
        List<CreditCardInfo> creditCardInfoList = new ArrayList<>();
        CreditCardInfo creditCardInfo = buildCreditCardInfo();
        creditCardInfoList.add(creditCardInfo);
        paymentSources.setCreditCardInfo(creditCardInfoList);
        return paymentSources;
    }

    private CreditCardInfo buildCreditCardInfo() {
        CreditCardInfo creditCardInfo = new CreditCardInfo();
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber("4263982640269299");
        creditCard.setExpirationMonth(11);
        creditCard.setExpirationYear(2018);
        creditCard.setSecurityCode(111);
        creditCard.setCardLastFourDigits("9299");
        creditCardInfo.setCreditCard(creditCard);
        return creditCardInfo;
    }

    private CreditCardInfo buildCreditCardInfo2() {
        CreditCardInfo creditCardInfo = new CreditCardInfo();
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber("4242091340768935");
        creditCard.setExpirationMonth(12);
        creditCard.setExpirationYear(2021);
        creditCard.setSecurityCode(388);
        creditCard.setCardLastFourDigits("8935");
        creditCardInfo.setCreditCard(creditCard);
        return creditCardInfo;
    }

    private CardTransaction buildVaultedShopperMerchantTransaction() {
        CardTransaction cardTransaction = new CardTransaction();
        CreditCard creditCard = new CreditCard();
        creditCard.setCardLastFourDigits("9299");
        creditCard.setCardType("VISA");
        cardTransaction.setCreditCard(creditCard);
        cardTransaction.setAmount(400.0);
        cardTransaction.setVaultedShopperId(22804805);
        cardTransaction.setSoftDescriptor("TestDecs");
        cardTransaction.setCurrency("EUR");
        cardTransaction.setCardTransactionType("AUTH_CAPTURE");
        VendorsInfo vendorsInfo = new VendorsInfo();
        List<VendorInfo> vendorInfoList = new ArrayList<>();
        VendorInfo vendorInfo = new VendorInfo();
        vendorInfo.setVendorId(581223L);
        vendorInfoList.add(vendorInfo);
        vendorsInfo.setVendorInfo(vendorInfoList);
        cardTransaction.setVendorsInfo(vendorsInfo);
        return cardTransaction;
    }
}
