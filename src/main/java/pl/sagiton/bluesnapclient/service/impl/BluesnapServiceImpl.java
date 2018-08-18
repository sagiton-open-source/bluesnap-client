package pl.sagiton.bluesnapclient.service.impl;

import lombok.extern.slf4j.Slf4j;
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
import pl.sagiton.bluesnapclient.service.exceptions.BluesnapExceptionUtils;
import pl.sagiton.bluesnapclient.service.interceptor.BasicAuthInterceptor;
import pl.sagiton.bluesnapclient.service.interceptor.LoggingInterceptor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BluesnapServiceImpl implements BluesnapService {

    private static final String TRANSACTIONS_URI = "/transactions";
    private static final String VAULTED_SHOPPER_URI = "/vaulted-shoppers";
    private static final String VENDORS_URI = "/vendors";
    private static final String BLUESNAP_CLIENT_ERROR = "Bluesnap client error:";
    private static final String BLUESNAP_REST_CLIENT_ERROR = "Bluesnap REST client error:";


    private static String bluesnapRootUri;
    private RestTemplate restTemplate;

    public BluesnapServiceImpl(String bluesnapRootUri, String bluesnapUsername, String bluesnapPassword) {
        this.restTemplate = createRestTemplate(bluesnapUsername, bluesnapPassword);
        this.bluesnapRootUri = bluesnapRootUri;
    }

    @Override
    public CardTransaction pay(CardTransaction cardTransaction) {
        try {
            return restTemplate.postForObject(bluesnapRootUri + TRANSACTIONS_URI, cardTransaction, CardTransaction.class);
        } catch (HttpClientErrorException e) {
            log.debug(BLUESNAP_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        } catch (RestClientException e) {
            log.debug(BLUESNAP_REST_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        }
    }

    @Override
    public VaultedShopper getVaultedShopper(String id) {
        try {
            return restTemplate.getForObject(bluesnapRootUri + VAULTED_SHOPPER_URI + "/" + id, VaultedShopper.class);
        } catch (HttpClientErrorException e) {
            log.debug(BLUESNAP_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        } catch (RestClientException e) {
            log.debug(BLUESNAP_REST_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        }
    }

    @Override
    public VaultedShopper createVaultedShopper(VaultedShopper vaultedShopper) {
        try {
            return restTemplate.postForObject(bluesnapRootUri + VAULTED_SHOPPER_URI, vaultedShopper, VaultedShopper.class);
        } catch (HttpClientErrorException e) {
            log.debug(BLUESNAP_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        } catch (RestClientException e) {
            log.debug(BLUESNAP_REST_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        }
    }

    @Override
    public void updateVaultedShopper(String id, VaultedShopper vaultedShopper) {
        try {
            restTemplate.put(bluesnapRootUri + VAULTED_SHOPPER_URI + "/" + id, vaultedShopper, VaultedShopper.class);
        } catch (HttpClientErrorException e) {
            log.debug(BLUESNAP_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        } catch (RestClientException e) {
            log.debug(BLUESNAP_REST_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        }
    }

    @Override
    public URI createVendor(Vendor vendor) {
        try {
            return restTemplate.postForLocation(bluesnapRootUri + VENDORS_URI, vendor);
        } catch (HttpClientErrorException e) {
            log.debug(BLUESNAP_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        } catch (RestClientException e) {
            log.debug(BLUESNAP_REST_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        }
    }

    @Override
    public Vendor getVendor(String id) {
        try {
            return restTemplate.getForObject(bluesnapRootUri + VENDORS_URI + "/" + id, Vendor.class);
        } catch (HttpClientErrorException e) {
            log.debug(BLUESNAP_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        } catch (RestClientException e) {
            log.debug(BLUESNAP_REST_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        }
    }

    @Override
    public void updateVendor(String id, @RequestBody Vendor vendor) {
        try {
            restTemplate.put(bluesnapRootUri + VENDORS_URI + "/" + id, vendor, Vendor.class);
        } catch (HttpClientErrorException e) {
            log.debug(BLUESNAP_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        } catch (RestClientException e) {
            log.debug(BLUESNAP_REST_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        }
    }

    @Override
    public VendorList getVendors() {
        try {
            return restTemplate.getForObject(bluesnapRootUri + VENDORS_URI, VendorList.class);
        } catch (HttpClientErrorException e) {
            log.debug(BLUESNAP_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        } catch (RestClientException e) {
            log.debug(BLUESNAP_REST_CLIENT_ERROR, e);
            throw BluesnapExceptionUtils.wrapException(new BluesnapException(e));
        }
    }

    private RestTemplate createRestTemplate(String bluesnapUsername, String bluesnapPassword) {
        RestTemplate restTemplate = new RestTemplate();
        final List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new BasicAuthInterceptor(bluesnapUsername, bluesnapPassword));
        interceptors.add(new LoggingInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}