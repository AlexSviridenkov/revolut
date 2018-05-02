package moneytransfer.rest;

import java.math.BigDecimal;

public class TransferMoneyRequest {
    public int from;
    public int to;
    public BigDecimal amount;
}
