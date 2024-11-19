package com.springboot.bankbackend.exception;

public class BodyGuardException extends RuntimeException{

    public BodyGuardException(String str){
        super(str);
    }
}
