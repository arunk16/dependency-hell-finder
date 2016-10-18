package com.dh;

import java.util.ArrayList;
import java.util.List;

public class ServiceAttributes {
    private String version = "";
    private List<String> dependencies;
    private List<String> listensToQueues;
    private List<String> broadcastsToQueues;

    public ServiceAttributes() {
        this.dependencies = new ArrayList<String>();
        this.listensToQueues = new ArrayList<String>();
        this.broadcastsToQueues = new ArrayList<String>();
    }

    public String getVersion() {
        return this.version;
    }

    public List<String> getDependencies() {
        return this.dependencies;
    }

    public List<String> getListensToQueues() {
        return this.listensToQueues;
    }

    public List<String> getBroadcastsToQueues() {
        return this.broadcastsToQueues;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
