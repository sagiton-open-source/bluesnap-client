package pl.sagiton.bluesnapclient.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import pl.sagiton.bluesnapclient.model.cardtransaction.CardHolder;
import pl.sagiton.bluesnapclient.model.cardtransaction.CardTransaction;
import pl.sagiton.bluesnapclient.model.cardtransaction.CreditCard;
import pl.sagiton.bluesnapclient.model.vaultedshopper.CreditCardInfo;
import pl.sagiton.bluesnapclient.model.vaultedshopper.PaymentSources;
import pl.sagiton.bluesnapclient.model.vaultedshopper.VaultedShopper;
import pl.sagiton.bluesnapclient.model.vendors.*;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BluesnapServiceImplMockTest {
    private BluesnapServiceImpl bluesnapServiceImpl;
    private static final String VENDOR_ID = "10400732";
    private static final String SHOPPER_ID = "22804805";
    private static final String NEW_LAST_NAME = "NewLastName";
    private static final String NEW_FIRST_NAME = "NewName";
    private static final String NEW_ADDRESS = "new test address";
    private static final String VENDORS_URI = "/vendors";
    private static final String SHOPPERS_URI = "/vaulted-shoppers";
    private static final String TRANSACTIONS_URI = "/transactions";


    private static final String BLUESNAP_ROOT_URI = "https://sandbox.bluesnap.com/services/2";
    private static final String BLUESNAP_USERNAME = "API_153192791914019503127";
    private static final String BLUESNAP_PASSWORD = "LajkonikTravel12#";
    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        bluesnapServiceImpl = new BluesnapServiceImpl(BLUESNAP_ROOT_URI, BLUESNAP_USERNAME, BLUESNAP_PASSWORD);
        try {

            Field declaredField = BluesnapServiceImpl.class.getDeclaredField("restTemplate");
            boolean accessible = declaredField.isAccessible();

            declaredField.setAccessible(true);
            declaredField.set(bluesnapServiceImpl, restTemplate);
            declaredField.setAccessible(accessible);

        } catch (NoSuchFieldException
                | SecurityException
                | IllegalArgumentException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldCreateNewVendor() throws URISyntaxException {
        Vendor vendor = buildVendor();
        URI uri = new URI("http://test.com");
        when(restTemplate
                .postForLocation(BLUESNAP_ROOT_URI + VENDORS_URI, vendor))
                .thenReturn(uri);
        URI response = bluesnapServiceImpl.createVendor(vendor);

        verify(restTemplate).postForLocation(
                Mockito.eq(BLUESNAP_ROOT_URI + "/vendors"),
                Mockito.eq(vendor)
        );
        assertNotNull(response);
        assertEquals(uri, response);

    }

    @Test
    public void shouldUpdateVendor() {
        Vendor vendor = buildVendorResponse();
        given(restTemplate
                .getForObject(BLUESNAP_ROOT_URI + VENDORS_URI + "/" + VENDOR_ID, Vendor.class))
                .willReturn(vendor);
        Vendor vendorResponse = bluesnapServiceImpl.getVendor(VENDOR_ID);
        assertNotNull(vendorResponse);

        String vendorResponseId = vendorResponse.getVendorId().toString();
        assertEquals(VENDOR_ID, vendorResponseId);

        vendorResponse.setLastName(NEW_LAST_NAME);
        vendorResponse.getVendorPrincipal().setFirstName(NEW_FIRST_NAME);
        List<PayoutInfo> payoutInfo = vendorResponse.getPayoutInfo();
        payoutInfo.get(0).setAddress(NEW_ADDRESS);
        vendorResponse.setPayoutInfo(payoutInfo);
        VendorPrincipal vendorPrincipalDataToUpdate = vendorResponse.getVendorPrincipal();

        bluesnapServiceImpl.updateVendor(VENDOR_ID, vendorResponse);
        verify(restTemplate)
                .put(BLUESNAP_ROOT_URI + VENDORS_URI + "/" + VENDOR_ID, vendorResponse, Vendor.class);

        given(restTemplate
                .getForObject(BLUESNAP_ROOT_URI + VENDORS_URI + "/" + VENDOR_ID, Vendor.class))
                .willReturn(vendorResponse);

        Vendor vendorAfterUpdate = bluesnapServiceImpl.getVendor(VENDOR_ID);
        VendorPrincipal vendorPrincipalAfterUpdate = vendorAfterUpdate.getVendorPrincipal();
        String vendorLastNameAfterUpdate = vendorAfterUpdate.getLastName();
        assertEquals(NEW_LAST_NAME, vendorLastNameAfterUpdate);
        assertEquals(vendorPrincipalDataToUpdate, vendorPrincipalAfterUpdate);
    }

    @Test
    public void shouldUpdateVendorPayoutInfo() {
        Vendor vendorResponse = buildVendorResponse();
        given(restTemplate
                .getForObject(BLUESNAP_ROOT_URI + VENDORS_URI + "/" + VENDOR_ID, Vendor.class))
                .willReturn(vendorResponse);
        Vendor vendor = bluesnapServiceImpl.getVendor(VENDOR_ID);

        String vendorIdString = vendor.getVendorId().toString();
        assertEquals(VENDOR_ID, vendorIdString);

        PayoutInfo updatedPayoutInfo = buildPayoutInfo();
        List<PayoutInfo> payoutInfoList = new ArrayList<>();
        payoutInfoList.add(updatedPayoutInfo);
        vendor.setPayoutInfo(payoutInfoList);

        bluesnapServiceImpl.updateVendor(VENDOR_ID, vendor);
        verify(restTemplate)
                .put(BLUESNAP_ROOT_URI + VENDORS_URI + "/" + VENDOR_ID, vendor, Vendor.class);
        given(restTemplate
                .getForObject(BLUESNAP_ROOT_URI + VENDORS_URI + "/" + VENDOR_ID, Vendor.class))
                .willReturn(vendor);
        Vendor vendorAfterUpdate = bluesnapServiceImpl.getVendor(VENDOR_ID);
        assertEquals(vendor.getPayoutInfo(), vendorAfterUpdate.getPayoutInfo());
    }

    @Test
    public void shouldGetVendor() {
        Vendor vendorResponse = buildVendorResponse();
        given(restTemplate
                .getForObject(BLUESNAP_ROOT_URI + VENDORS_URI + "/" + VENDOR_ID, Vendor.class))
                .willReturn(vendorResponse);
        Vendor vendor = bluesnapServiceImpl.getVendor(VENDOR_ID);
        assertNotNull(vendor);
        String vendorIdFromResponse = vendor.getVendorId().toString();
        assertEquals(VENDOR_ID, vendorIdFromResponse);

    }

    @Test
    public void shouldGetVendors() {
        Vendor vendorResponse = buildVendor();
        VendorList vendorListResponse = new VendorList();
        List<Vendor> vendors = new ArrayList<>();
        vendors.add(vendorResponse);
        vendorListResponse.setVendor(vendors);
        given(restTemplate
                .getForObject(BLUESNAP_ROOT_URI + VENDORS_URI, VendorList.class))
                .willReturn(vendorListResponse);
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
        when(restTemplate
                .postForObject(BLUESNAP_ROOT_URI + TRANSACTIONS_URI, cardTransaction, CardTransaction.class))
                .thenReturn(cardTransaction);
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
        when(restTemplate
                .postForObject(BLUESNAP_ROOT_URI + TRANSACTIONS_URI, cardTransaction, CardTransaction.class))
                .thenReturn(cardTransaction);
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
        VaultedShopper vaultedShopperResponseMock = buildVaultedShopperResponse();
        when(restTemplate
                .postForObject(BLUESNAP_ROOT_URI + SHOPPERS_URI, vaultedShopper, VaultedShopper.class))
                .thenReturn(vaultedShopperResponseMock);
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
        assertEquals(creditCardExpirationMonthRequest, creditCardExpirationMonthResponse);
        assertEquals(creditCardExpirationYearRequest, creditCardExpirationYearResponse);
    }

    @Test
    public void shouldUpdateVaultedShopper() {
        VaultedShopper vaultedShopper = buildVaultedShopperResponse();
        when(restTemplate
                .getForObject(BLUESNAP_ROOT_URI + SHOPPERS_URI + "/" + SHOPPER_ID, VaultedShopper.class))
                .thenReturn(vaultedShopper);
        VaultedShopper vaultedShopperBeforeChange = bluesnapServiceImpl.getVaultedShopper(SHOPPER_ID);
        vaultedShopperBeforeChange.setLastName(NEW_LAST_NAME);
        bluesnapServiceImpl.updateVaultedShopper(SHOPPER_ID, vaultedShopperBeforeChange);
        verify(restTemplate)
                .put(BLUESNAP_ROOT_URI + SHOPPERS_URI + "/" + SHOPPER_ID, vaultedShopperBeforeChange, VaultedShopper.class);
        when(restTemplate
                .getForObject(BLUESNAP_ROOT_URI + SHOPPERS_URI + "/" + SHOPPER_ID, VaultedShopper.class))
                .thenReturn(vaultedShopperBeforeChange);
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
        VaultedShopper vaultedShopperResponse = buildVaultedShopperResponse();
        when(restTemplate
                .getForObject(BLUESNAP_ROOT_URI + SHOPPERS_URI + "/" + SHOPPER_ID, VaultedShopper.class))
                .thenReturn(vaultedShopperResponse);
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
        VaultedShopper vaultedShopperResponseMock = buildVaultedShopperResponse();
        when(restTemplate
                .getForObject(BLUESNAP_ROOT_URI + SHOPPERS_URI + "/" + SHOPPER_ID, VaultedShopper.class))
                .thenReturn(vaultedShopperResponseMock);
        VaultedShopper vaultedShopper = bluesnapServiceImpl.getVaultedShopper(SHOPPER_ID);
        CreditCardInfo creditCardInfo = buildCreditCardInfo2();
        PaymentSources paymentSources = vaultedShopper.getPaymentSources();
        List<CreditCardInfo> creditCardInfoList = paymentSources.getCreditCardInfo();
        creditCardInfoList.add(creditCardInfo);
        paymentSources.setCreditCardInfo(creditCardInfoList);
        vaultedShopper.setPaymentSources(paymentSources);
        bluesnapServiceImpl.updateVaultedShopper(SHOPPER_ID, vaultedShopper);
        verify(restTemplate)
                .put(BLUESNAP_ROOT_URI + SHOPPERS_URI + "/" + SHOPPER_ID, vaultedShopper, VaultedShopper.class);
        when(restTemplate
                .getForObject(BLUESNAP_ROOT_URI + SHOPPERS_URI + "/" + SHOPPER_ID, VaultedShopper.class))
                .thenReturn(vaultedShopper);
        VaultedShopper vaultedShopperResponse = bluesnapServiceImpl.getVaultedShopper(SHOPPER_ID);

        assertNotNull(vaultedShopper.getVaultedShopperId());

        assertEquals(SHOPPER_ID, vaultedShopper.getVaultedShopperId().toString());
        assertEquals(2, vaultedShopperResponse.getPaymentSources().getCreditCardInfo().size());
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
        assertEquals(creditCardExpirationMonthRequest, creditCardExpirationMonthResponse);
        assertEquals(creditCardExpirationYearRequest, creditCardExpirationYearResponse);
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

    private Vendor buildVendorResponse() {
        Vendor vendorResponse = buildVendor();
        vendorResponse.setVendorId(10400732);
        return vendorResponse;
    }

    private VaultedShopper buildVaultedShopperResponse() {
        VaultedShopper vaultedShopperResponse = buildVaultedShopper();
        vaultedShopperResponse.setVaultedShopperId(22804805);
        return vaultedShopperResponse;
    }
}
