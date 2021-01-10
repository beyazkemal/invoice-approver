package com.kemalbeyaz.invoice.approver.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author kemal.beyaz
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper {

    private final String message;
    private final String detail;
    private final Object data;

    /**
     * Hatalı cevap için constructor.
     */
    public ResponseWrapper(String message, String detail) {
        this.message = message;
        this.detail = detail;
        this.data = null;
    }

    /**
     * Başarılı cevap için constructor.
     */
    public ResponseWrapper(String message, Object data) {
        this.message = message;
        this.detail = null;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

    public Object getData() {
        return data;
    }
}
