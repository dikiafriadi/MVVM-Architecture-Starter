package com.aditp.mdvkarch.data.remote.api_response;

import com.google.gson.annotations.SerializedName;

public class License {

    @SerializedName("name")
    private String name;

    @SerializedName("spdx_id")
    private String spdxId;

    @SerializedName("key")
    private String key;

    @SerializedName("url")
    private String url;

    @SerializedName("node_id")
    private String nodeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpdxId() {
        return spdxId;
    }

    public void setSpdxId(String spdxId) {
        this.spdxId = spdxId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}