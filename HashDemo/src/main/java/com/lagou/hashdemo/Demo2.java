package com.lagou.hashdemo;


import java.util.SortedMap;
import java.util.TreeMap;

//⼀致性Hash算法实现（不含虚拟节点）
public class Demo2 {

    public static void main(String[] args) {

        String[] tomcatServices = {"1278.12.366","127.3.25.63.5.1","127.3.25.63.5.2","127.03.25.63.5.3","127.3.25.63.54"};

        // 容器 存放 tomcat 服务器与其对应的 IP哈希
        SortedMap<Integer,String> map = new TreeMap<>();

        for (String tomcatService : tomcatServices) {

            int tomcatHash = Math.abs(tomcatService.hashCode());

            map.put(tomcatHash,tomcatService);

        }



        String[] clientsServices = {"10.78.12.366","113.25.63.51","126.12.3.688"};

        for (String clientsService : clientsServices) {

            int clientHash = Math.abs(clientsService.hashCode());

            SortedMap<Integer, String> integerStringSortedMap = map.tailMap(clientHash);

            // 为空 为tomcat 第一个节点
            if(integerStringSortedMap.isEmpty()){

                Integer firstKey = map.firstKey();
                System.out.println("==========>>>>客户端：" + clientsService + " 被 路由到服务器：" + map.get(firstKey));
            }else {
                Integer key = integerStringSortedMap.firstKey();
                System.out.println("==========>>>>客户端：" + clientsService + " 被 路由到服务器：" + map.get(key));
            }

        }
    }

}
