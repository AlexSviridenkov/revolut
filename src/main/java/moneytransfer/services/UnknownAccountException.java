package moneytransfer.services;

public class UnknownAccountException extends Error {
    UnknownAccountException(int id){
        super("Account id=" + id + " does not exist");
    }
}

