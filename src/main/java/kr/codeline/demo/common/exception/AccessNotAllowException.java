package kr.codeline.demo.common.exception;

public class AccessNotAllowException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccessNotAllowException(String message) {
        super(message);
    }
}