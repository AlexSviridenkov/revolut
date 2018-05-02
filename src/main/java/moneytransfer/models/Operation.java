package moneytransfer.models;

import java.math.BigDecimal;
import java.util.Date;

public class Operation {
    public int id;
    public Date transferDate;
    public int accountIdFrom;
    public int accountIdTo;
    public BigDecimal amount;
}
