package com.example.premierleagueapp.core.data.source.remote.rxerrorhandling;

public class PremierLeagueException extends Exception {

    private String data;
    private int statusCode;
    private int errorCode;
    private Kind kind;

    public PremierLeagueException(Kind kind) {
        this.kind = kind;
    }

    public PremierLeagueException(Kind kind, String message) {
        super(message);
        this.kind = kind;
    }

    public PremierLeagueException(Kind kind, String message, Throwable cause) {
        super(message, cause);
        this.kind = kind;
    }

    public PremierLeagueException(Kind kind, Throwable cause) {
        super(cause);
        this.kind = kind;
    }

    public String getData() {
        return data;
    }

    public PremierLeagueException setData(String data) {
        this.data = data;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public PremierLeagueException setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public PremierLeagueException setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Kind getKind() {
        return kind;
    }


    public enum Kind {
        /**
         * An {@link java.io.IOException} occurred while communicating to the server.
         */

        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */

        HTTP,
        /**
         * An internal error occurred while attempting to build a request. It is best practice to
         * re-throw this exception so your application crashes.
         */

        UNEXPECTED,


        SERVER_DOWN,


        TIME_OUT,


        UNAUTHORIZED,

        NOT_MODIFIED

    }

}
