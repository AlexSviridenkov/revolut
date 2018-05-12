package moneytransfer.services;

public class UnknownAccountException extends Error {
    UnknownAccountException(String message){
        super(message);
    }
}

