package com.lagou.server;

import com.lagou.entity.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 远程服务对象实现类写在服务端；必须继承UnicastRemoteObject或其子类
 **/
public class HelloImpl extends UnicastRemoteObject implements Hello {

    private static final long serialVersionID =  3638546195897885959L;

    /**
     * 因为UnicastRemoteObject的构造方法抛出了RemoteException异常，
     * 因此这里默认的构造方法必须写，必须声明 抛出RemoteException异常
     * @throws RemoteException
     */
    protected HelloImpl() throws RemoteException {
    }

    @Override
    public String sayHello(User user) throws RemoteException {

        System.out.println("this is server,hello " + user.getName());

        return "success";
    }
}
