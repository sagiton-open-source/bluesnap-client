package pl.sagiton.bluesnapclient.service;

import pl.sagiton.bluesnapclient.model.cardtransaction.CardTransaction;
import pl.sagiton.bluesnapclient.model.vaultedshopper.VaultedShopper;
import pl.sagiton.bluesnapclient.model.vendors.Vendor;
import pl.sagiton.bluesnapclient.model.vendors.VendorList;


import java.net.URI;

public interface BluesnapService {

    public CardTransaction pay(CardTransaction cardTransaction);

    public VaultedShopper getVaultedShopper(String id);

    public VaultedShopper createVaultedShopper(VaultedShopper vaultedShopper);

    public void updateVaultedShopper(String id, VaultedShopper vaultedShopper);

    public URI createVendor(Vendor vendor);

    public Vendor getVendor(String id);

    public void updateVendor(String id, Vendor vendor);

    public VendorList getVendors();
}
