package com.lagou.component;

import java.util.ArrayList;
import java.util.List;


public class Server {
    private List<Service> serviceList = new ArrayList<>();

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }
    public void addService(Service service){
        serviceList.add(service);
    }
}
