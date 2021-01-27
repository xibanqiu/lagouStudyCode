package com.lagou.server;

import com.lagou.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 远程服务对象接口必须继承Remote接口；同时方法必须抛出RemoteExceptino异常
 */
public interface Hello extends Remote {

    public String sayHello(User user) throws RemoteException;
    
}

