package com.aditp.mdvkarch.data.remote.model_response.login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLogin {

    @SerializedName("notices")
    private List<Object> notices;

    @SerializedName("data")
    private Data data;

    @SerializedName("warnings")
    private List<Object> warnings;

    @SerializedName("errors")
    private List<Error> errors;

    @SerializedName("status")
    private String status;

    public void setNotices(List<Object> notices) {
        this.notices = notices;
    }

    public List<Object> getNotices() {
        return notices;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}