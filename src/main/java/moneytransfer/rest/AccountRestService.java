package moneytransfer.rest;

import moneytransfer.models.Account;
import moneytransfer.services.AccountService;
import moneytransfer.services.NotEnoughMoneyException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/accounts")
public class AccountRestService {

    @Inject
    private AccountService accountService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public AccountInfoResponse create() throws SQLException {
        return convert(accountService.create());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public AccountInfoResponse info(@PathParam("id") int id) throws SQLException {
        return convert(accountService.getAccount(id));
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void delete(@PathParam("id") int id) throws SQLException {
        accountService.delete(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/putmoney")
    public AccountInfoResponse putMoney(PutMoneyRequest request) throws SQLException {
        return convert(accountService.putMoney(request.accountId, request.amount));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/transfer")
    public void transfer(TransferMoneyRequest request) throws NotEnoughMoneyException, SQLException {
        accountService.transfer(request.from, request.to, request.amount);
    }

    private AccountInfoResponse convert(Account account){
        if (account == null)
            return null;

        AccountInfoResponse info = new AccountInfoResponse();
        info.id = account.id;
        info.balance = account.balance;
        return info;
    }
}
