package pl.sagiton.bluesnapclient.service.impl;

import lombok.extern.java.Log;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.sagiton.bluesnapclient.model.cardtransaction.CardTransaction;
import pl.sagiton.bluesnapclient.model.vaultedshopper.VaultedShopper;
import pl.sagiton.bluesnapclient.model.vendors.Vendor;
import pl.sagiton.bluesnapclient.model.vendors.VendorList;
import pl.sagiton.bluesnapclient.service.BluesnapService;
import pl.sagiton.bluesnapclient.service.exceptions.BluesnapException;
import pl.sagiton.bluesnapclient.service.exceptions.BluesnapRESTException;
import pl.sagiton.bluesnapclient.service.interceptor.BasicAuthInterceptor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
public class BluesnapServiceImpl implements BluesnapService {

    private static final String TRANSACTIONS_URI = "/transactions";
    private static final String VAULTED_SHOPPER_URI = "/vaulted-shoppers";
    private static final String VENDORS_URI = "/vendors";
    private static  final String BLUESNAP_CLIENT_ERROR = "Bluesnap client error:";
    private static  final String BLUESNAP_REST_CLIENT_ERROR = "Bluesnap REST client error:";


    private static String bluesnapRootUri;
    private RestTemplate restTemplate;

    public BluesnapServiceImpl(String bluesnapRootUri, String bluesnapUsername, String bluesnapPassword) {
        this.restTemplate = createRestTemplate(bluesnapUsername, bluesnapPassword);
        this.bluesnapRootUri = bluesnapRootUri;
    }

    @Override
    public CardTransaction pay(CardTransaction cardTransaction)  throws BluesnapException, BluesnapRESTException {
         try {
             return restTemplate.postForObject(bluesnapRootUri + TRANSACTIONS_URI, cardTransaction, CardTransaction.class);
         }
         catch (HttpClientErrorException e) {
             log.log(Level.SEVERE, BLUESNAP_CLIENT_ERROR, e);
            throw new BluesnapException(e.getStatusCode(),e.getStatusText(),e.getResponseHeaders(),e.getResponseBodyAsByteArray(),null);
        }
         catch (RestClientException e){
             log.log(Level.SEVERE, BLUESNAP_REST_CLIENT_ERROR, e);
             throw new BluesnapRESTException(e.getMessage(), e.fillInStackTrace());
         }
    }
    @Override
    public VaultedShopper getVaultedShopper(String id) throws BluesnapException, BluesnapRESTException {
        try{
        return restTemplate.getForObject(bluesnapRootUri + VAULTED_SHOPPER_URI + "/" + id, VaultedShopper.class);
        }
        catch (HttpClientErrorException e) {
            log.log(Level.SEVERE, BLUESNAP_CLIENT_ERROR, e);
            throw new BluesnapException(e.getStatusCode(),e.getStatusText(),e.getResponseHeaders(),e.getResponseBodyAsByteArray(),null);
        }
        catch (RestClientException e){
            log.log(Level.SEVERE, BLUESNAP_REST_CLIENT_ERROR, e);
            throw new BluesnapRESTException(e.getMessage(), e.fillInStackTrace());
        }
    }
    @Override
    public VaultedShopper createVaultedShopper(VaultedShopper vaultedShopper)  throws BluesnapException, BluesnapRESTException  {
        try{
        return restTemplate.postForObject(bluesnapRootUri + VAULTED_SHOPPER_URI, vaultedShopper, VaultedShopper.class);
        }
        catch (HttpClientErrorException e) {
            log.log(Level.SEVERE, BLUESNAP_CLIENT_ERROR, e);
            throw new BluesnapException(e.getStatusCode(),e.getStatusText(),e.getResponseHeaders(),e.getResponseBodyAsByteArray(),null);
        }
        catch (RestClientException e){
            log.log(Level.SEVERE, BLUESNAP_REST_CLIENT_ERROR, e);
            throw new BluesnapRESTException(e.getMessage(), e.fillInStackTrace());
        }
    }
    @Override
    public void updateVaultedShopper(String id, VaultedShopper vaultedShopper) throws BluesnapException, BluesnapRESTException  {
        try{
        restTemplate.put(bluesnapRootUri + VAULTED_SHOPPER_URI + "/" + id, vaultedShopper, VaultedShopper.class);
        }
        catch (HttpClientErrorException e) {
            log.log(Level.SEVERE, BLUESNAP_CLIENT_ERROR, e);
            throw new BluesnapException(e.getStatusCode(),e.getStatusText(),e.getResponseHeaders(),e.getResponseBodyAsByteArray(),null);
        }
        catch (RestClientException e){
            log.log(Level.SEVERE, BLUESNAP_REST_CLIENT_ERROR, e);
            throw new BluesnapRESTException(e.getMessage(), e.fillInStackTrace());
        }
    }
    @Override
    public URI createVendor(Vendor vendor) throws BluesnapException, BluesnapRESTException  {
        try{
        return restTemplate.postForLocation(bluesnapRootUri + VENDORS_URI, vendor);
        }
        catch (HttpClientErrorException e) {
            log.log(Level.SEVERE, BLUESNAP_CLIENT_ERROR, e);
            throw new BluesnapException(e.getStatusCode(),e.getStatusText(),e.getResponseHeaders(),e.getResponseBodyAsByteArray(),null);
        }
        catch (RestClientException e){
            log.log(Level.SEVERE, BLUESNAP_REST_CLIENT_ERROR, e);
            throw new BluesnapRESTException(e.getMessage(), e.fillInStackTrace());
        }
    }
    @Override
    public Vendor getVendor(String id) throws BluesnapException, BluesnapRESTException  {
        try{
        return restTemplate.getForObject(bluesnapRootUri + VENDORS_URI + "/" + id, Vendor.class);
        }
        catch (HttpClientErrorException e) {
            log.log(Level.SEVERE, BLUESNAP_CLIENT_ERROR, e);
            throw new BluesnapException(e.getStatusCode(),e.getStatusText(),e.getResponseHeaders(),e.getResponseBodyAsByteArray(),null);
        }
        catch (RestClientException e){
            log.log(Level.SEVERE, BLUESNAP_REST_CLIENT_ERROR, e);
            throw new BluesnapRESTException(e.getMessage(), e.fillInStackTrace());
        }
    }
    @Override
    public void updateVendor(String id, @RequestBody Vendor vendor) throws BluesnapException, BluesnapRESTException  {
        try{
        restTemplate.put(bluesnapRootUri + VENDORS_URI + "/" + id, vendor, Vendor.class);
        }
        catch (HttpClientErrorException e) {
            log.log(Level.SEVERE, BLUESNAP_CLIENT_ERROR, e);
            throw new BluesnapException(e.getStatusCode(),e.getStatusText(),e.getResponseHeaders(),e.getResponseBodyAsByteArray(),null);
        }
        catch (RestClientException e){
            log.log(Level.SEVERE, BLUESNAP_REST_CLIENT_ERROR, e);
            throw new BluesnapRESTException(e.getMessage(), e.fillInStackTrace());
        }
    }

   @Override
    public VendorList getVendors() throws BluesnapException, BluesnapRESTException  {
        try{
        return restTemplate.getForObject(bluesnapRootUri + VENDORS_URI, VendorList.class);
        }
        catch (HttpClientErrorException e) {
            log.log(Level.SEVERE, BLUESNAP_CLIENT_ERROR, e);
            throw new BluesnapException(e.getStatusCode(),e.getStatusText(),e.getResponseHeaders(),e.getResponseBodyAsByteArray(),null);
        }
        catch (RestClientException e){
            log.log(Level.SEVERE, BLUESNAP_REST_CLIENT_ERROR, e);
            throw new BluesnapRESTException(e.getMessage(), e.fillInStackTrace());
        }
   }

    private RestTemplate createRestTemplate(String bluesnapUsername, String bluesnapPassword) {
        RestTemplate restTemplate = new RestTemplate();
        final List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new BasicAuthInterceptor(bluesnapUsername, bluesnapPassword));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}