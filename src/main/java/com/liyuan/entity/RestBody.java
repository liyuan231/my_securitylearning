package com.liyuan.entity;

public class RestBody<T> implements Rest<T>{
    private int httpStatus = 200;
    private T data;
    private String message = "";
    private String identifier = "";

    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getmessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets identifier.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets identifier.
     *
     * @param identifier the identifier
     */
    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Ok rest.
     *
     * @return the rest
     */
    public static Rest ok() {
        return new RestBody();
    }

    /**
     * Ok rest.
     *
     * @param message the message
     * @return the rest
     */
    public static Rest ok(String message) {
        Rest restBody = new RestBody();
        restBody.setMessage(message);
        return restBody;
    }

    /**
     * Ok data rest.
     *
     * @param <T> the type parameter
     * @return the rest
     */
    public static <T> Rest<T> okData(T data) {
        Rest<T> restBody = new RestBody<>();
        restBody.setData(data);
        return restBody;
    }

    public static <T> Rest<T> okData(T data, String message) {
        Rest<T> restBody = new RestBody<>();
        restBody.setData(data);
        restBody.setMessage(message);
        return restBody;
    }

    /**
     * Build rest.
     *
     * @param <T>        the type parameter
     * @param httpStatus the http status
     * @param data       the data
     * @param message        the message
     * @param identifier the identifier
     * @return the rest
     */
    public static <T> Rest<T> build(int httpStatus, T data, String message, String identifier) {
        Rest<T> restBody = new RestBody<>();
        restBody.setHttpStatus(httpStatus);
        restBody.setData(data);
        restBody.setMessage(message);
        restBody.setIdentifier(identifier);
        return restBody;
    }


    /**
     * Failure rest.
     *
     * @param message the message
     * @return the rest
     */
    public static Rest failure(int httpStatus, String message ) {
        Rest restBody = new RestBody();
        restBody.setHttpStatus(httpStatus);
        restBody.setMessage(message);
        restBody.setIdentifier("-9999");
        return restBody;
    }

    /**
     * Failure data rest.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @param message  the message
     * @return the rest
     */
    public static <T> Rest<T> failureData(T data, String message, String identifier) {
        Rest<T> restBody = new RestBody<>();
        restBody.setIdentifier(identifier);
        restBody.setData(data);
        restBody.setMessage(message);
        return restBody;
    }

    @Override
    public String toString() {
        return "{" +
                "httpStatus:" + httpStatus +
                ", data:" + data +
                ", message:" + message +
                ", identifier:" + identifier +
                '}';
    }
}
