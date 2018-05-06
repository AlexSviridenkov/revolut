package moneytransfer.services;

public class NotEnoughMoneyException extends Error {
    NotEnoughMoneyException(String message){
        super(message);
    }
}

