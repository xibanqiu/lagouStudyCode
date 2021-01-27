package com.lagou.server;

import com.lagou.entity.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello {

    private static final long serialVersionID =  3638546195897885959L;

    protected HelloImpl() throws RemoteException {
    }

    @Override
    public String sayHello(User user) throws RemoteException {

        System.out.println("this is server,hello " + user.getName());

        return "success";
    }
}
