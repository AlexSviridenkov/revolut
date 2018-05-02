package moneytransfer;

import moneytransfer.rest.AccountInfoResponse;
import moneytransfer.rest.PutMoneyRequest;
import moneytransfer.rest.TransferMoneyRequest;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class TestApplication extends JerseyTest {

    @Override
    protected Application configure() {
        return new ApplicationConfig();
    }

    @Test
    public void testManageAccount() {
        AccountInfoResponse createInfo = target("accounts/create").request().post(Entity.json(null), AccountInfoResponse.class);
        assertTrue(createInfo != null);
        assertTrue(createInfo.balance.compareTo(BigDecimal.ZERO) == 0);

        AccountInfoResponse getInfo = target("accounts/" + createInfo.id).request().get(AccountInfoResponse.class);
        assertTrue(getInfo != null);
        assertTrue(getInfo.id == createInfo.id);
        assertTrue(getInfo.balance.compareTo(BigDecimal.ZERO) == 0);

        target("accounts/" + createInfo.id).request().delete();
        getInfo = target("accounts/" + createInfo.id).request().get(AccountInfoResponse.class);
        assertTrue(getInfo == null);
    }

    @Test
    public void testPutMoney() {
        AccountInfoResponse createInfo = target("accounts/create").request().post(Entity.json(null), AccountInfoResponse.class);

        PutMoneyRequest request = new PutMoneyRequest();
        request.accountId = createInfo.id;
        request.amount = new BigDecimal(120);

        target("accounts/putmoney").request().post(Entity.entity(request, MediaType.APPLICATION_JSON), AccountInfoResponse.class);

        target("accounts/putmoney").request().post(Entity.entity(request, MediaType.APPLICATION_JSON), AccountInfoResponse.class);

        final AccountInfoResponse info = target("accounts/" + createInfo.id).request().get(AccountInfoResponse.class);
        assertTrue(info.balance.compareTo(new BigDecimal(240)) == 0);
    }

    @Test
    public void testTransferMoney() {
        AccountInfoResponse from = target("accounts/create").request().post(Entity.json(null), AccountInfoResponse.class);

        AccountInfoResponse to = target("accounts/create").request().post(Entity.json(null), AccountInfoResponse.class);

        PutMoneyRequest putRequest = new PutMoneyRequest();
        putRequest.accountId = from.id;
        putRequest.amount = new BigDecimal(250);

        target("accounts/putmoney").request().post(Entity.entity(putRequest, MediaType.APPLICATION_JSON), AccountInfoResponse.class);

        putRequest = new PutMoneyRequest();
        putRequest.accountId = to.id;
        putRequest.amount = new BigDecimal(310);
        target("accounts/putmoney").request().post(Entity.entity(putRequest, MediaType.APPLICATION_JSON), AccountInfoResponse.class);


        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest();
        transferMoneyRequest.from = from.id;
        transferMoneyRequest.to = to.id;
        transferMoneyRequest.amount = new BigDecimal(100);
        target("accounts/transfer").request().post(Entity.entity(transferMoneyRequest, MediaType.APPLICATION_JSON), AccountInfoResponse.class);

        AccountInfoResponse info = target("accounts/" + from.id).request().get(AccountInfoResponse.class);
        assertTrue(info.balance.compareTo(new BigDecimal(150)) == 0);

        info = target("accounts/" + to.id).request().get(AccountInfoResponse.class);
        assertTrue(info.balance.compareTo(new BigDecimal(410)) == 0);
    }
}