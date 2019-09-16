package com.sv.resp.bean;

public class ClientRespBean {

    private String status = null;
    private String statusCode = null;
    private String statusDesc = null;
    private Object details = null;

    public ClientRespBean() {

    }








    public ClientRespBean(String status, String statusCode, String statusDesc, Object details) {
        super();
        this.status = status;
        this.statusCode = statusCode;
        this.statusDesc = statusDesc;
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

}
