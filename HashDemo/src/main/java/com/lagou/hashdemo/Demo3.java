package com.lagou.hashdemo;

import java.util.SortedMap;
import java.util.TreeMap;

//⼀致性Hash算法实现（含虚拟节点）
public class Demo3 {


    public static void main(String[] args) {

        //step1 初始化：把服务器节点IP的哈希值对应到哈希环上
        // 定义服务器ip
        String[] tomcatServers = new String[] {"123.111.0.0","123.101.3.1","111.20.35.2","123.98.26.3"};
        SortedMap<Integer,String> map= new TreeMap<>();

        // 虚拟节点数
        int virtualCount = 3;

        for (String tomcatServer : tomcatServers) {

            int tomcatHash = Math.abs(tomcatServer.hashCode());

            map.put(tomcatHash,tomcatServer);

            for (int i = 0; i < virtualCount; i++) {

                int virtualHash = Math.abs((tomcatServer+"#"+i).hashCode());

                map.put(virtualHash,"----由虚拟节点"+ i + "映射过来的请求："+ tomcatServer);

            }

        }

        //step2 针对客户端IP求出hash值
        // 定义客户端IP
        String[] clients = new String[]  {"10.78.12.3","113.25.63.1","126.12.3.8"};

        for (String client : clients) {

            int clientHash = Math.abs(client.hashCode());

            SortedMap<Integer, String> integerStringSortedMap = map.tailMap(clientHash);

            if(integerStringSortedMap.isEmpty()){
                Integer firstKey = map.firstKey();

                System.out.println("==========>>>>客户端：" + client + " 被路由到服务器：" + map.get(firstKey));

            }else {
                Integer firstKey = integerStringSortedMap.firstKey();

                System.out.println("==========>>>>客户端：" + client + " 被路由到服务器：" + integerStringSortedMap.get(firstKey));
            }


        }



    }

}
