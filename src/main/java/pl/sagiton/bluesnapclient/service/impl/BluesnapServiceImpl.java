package pl.sagiton.bluesnapclient.service.impl;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import pl.sagiton.bluesnapclient.model.cardtransaction.CardTransaction;
import pl.sagiton.bluesnapclient.model.vaultedshopper.VaultedShopper;
import pl.sagiton.bluesnapclient.model.vendors.Vendor;
import pl.sagiton.bluesnapclient.model.vendors.VendorList;
import pl.sagiton.bluesnapclient.service.BluesnapService;
import pl.sagiton.bluesnapclient.service.interceptor.BasicAuthInterceptor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class BluesnapServiceImpl implements BluesnapService {

    private static final String TRANSACTIONS_URI = "/transactions";
    private static final String VAULTED_SHOPPER_URI = "/vaulted-shoppers";
    private static final String VENDORS_URI = "/vendors";

    private static String bluesnapRootUri;
    private RestTemplate restTemplate;

    public BluesnapServiceImpl(String bluesnapRootUri, String bluesnapUsername, String bluesnapPassword) {
        this.restTemplate = createRestTemplate(bluesnapUsername, bluesnapPassword);
        this.bluesnapRootUri = bluesnapRootUri;
    }
    @Override
    public CardTransaction pay(CardTransaction cardTransaction) {
        return restTemplate.postForObject(bluesnapRootUri + TRANSACTIONS_URI, cardTransaction, CardTransaction.class);
    }
    @Override
    public VaultedShopper getVaultedShopper(String id) {
        return restTemplate.getForObject(bluesnapRootUri + VAULTED_SHOPPER_URI + "/" + id, VaultedShopper.class);
    }
    @Override
    public VaultedShopper createVaultedShopper(VaultedShopper vaultedShopper) {
        return restTemplate.postForObject(bluesnapRootUri + VAULTED_SHOPPER_URI, vaultedShopper, VaultedShopper.class);
    }
    @Override
    public void updateVaultedShopper(String id, VaultedShopper vaultedShopper) {
        restTemplate.put(bluesnapRootUri + VAULTED_SHOPPER_URI + "/" + id, vaultedShopper, VaultedShopper.class);
    }
    @Override
    public URI createVendor(Vendor vendor) {
        return restTemplate.postForLocation(bluesnapRootUri + VENDORS_URI, vendor);
    }
    @Override
    public Vendor getVendor(String id) {
        return restTemplate.getForObject(bluesnapRootUri + VENDORS_URI + "/" + id, Vendor.class);
    }
    @Override
    public void updateVendor(String id, @RequestBody Vendor vendor) {
        restTemplate.put(bluesnapRootUri + VENDORS_URI + "/" + id, vendor, Vendor.class);
    }

   @Override
    public VendorList getVendors() {
        return restTemplate.getForObject(bluesnapRootUri + VENDORS_URI, VendorList.class);
    }

    private RestTemplate createRestTemplate(String bluesnapUsername, String bluesnapPassword) {
        RestTemplate restTemplate = new RestTemplate();
        final List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new BasicAuthInterceptor(bluesnapUsername, bluesnapPassword));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}