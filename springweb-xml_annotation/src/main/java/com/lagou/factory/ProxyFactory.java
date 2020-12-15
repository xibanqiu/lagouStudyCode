package com.lagou.factory;

import com.lagou.utils.TransactionManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class ProxyFactory {

    @Autowired
    private TransactionManager transactionManager;

//    public void setTransactionManager(TransactionManager transactionManager){
//        this.transactionManager = transactionManager;
//    }

    /**
     *JDK 动态代理的 工厂类
     *
     * @param object 委托对象
     * @return 代理对象
     */
    public Object getObjByJdkProxy(Object object){

        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

                Object result = null;

                try {
                    //开启事务管理
                    transactionManager.beginTransaction();

                    result =  method.invoke(object,objects);
                    //进行事务提交
                    transactionManager.commit();
                }catch (Exception e){
                    //进行事务回滚
                    transactionManager.rollback();
                    throw  e;
                }


                return result;
            }
        });

    }


    /**
     *Cglib 动态代理的 工厂类
     *
     * @param object 委托对象
     * @return 代理对象
     */
    public Object getObjByCglibProxy(Object object){

       return Enhancer.create(object.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                {

                    Object result = null;

                    try {
                        //开启事务管理
                        transactionManager.beginTransaction();

                        result =  method.invoke(object,objects);
                        //进行事务提交
                        transactionManager.commit();
                    }catch (Exception e){
                        //进行事务回滚
                        transactionManager.rollback();
                        throw  e;
                    }


                    return result;
                }
            }
        });

    }



}