package com.fish.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.fish.rpc.util.FishRPCConfig;
import com.fish.rpc.util.FishRPCLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * wenxy
 * 功能：
 * 日期：2020/1/15-18:07
 * 版本       开发者     描述
 * 1.0.0     wenxy     ...
 */
public class FishNacos {

    private static NamingService namingService;
    static {
        Properties properties = new Properties();
        properties.put("serverAddr", FishRPCConfig.getStringValue("nocas.server.addrs",""));
        properties.put("namespace",FishRPCConfig.getStringValue("ocas.namespace","public"));
        try {
            namingService = NamingFactory.createNamingService(properties);
        } catch (NacosException e) {
            FishRPCLog.error(e, e.getMessage());
        }
    }

    public static void registerInstance(String ip, int port) throws NacosException {
        registerInstance("fish.rpc.server",ip,port,"defaultcluster","DEFAULT_GROUP",0);
    }

    public static void registerInstance(String serverName,String ip, int port) throws NacosException {
        registerInstance(serverName,ip,port,"defaultcluster","DEFAULT_GROUP",0);
    }

    public static void registerInstance(String serverName,String ip, int port, String clusterName) throws NacosException {
        registerInstance(serverName,ip,port,clusterName,"DEFAULT_GROUP",0);
    }

    public static void registerInstance(String serverName,String ip, int port, String clusterName, String groupName) throws NacosException {
        registerInstance(serverName,ip,port,clusterName,groupName,0);
    }

    public static void registerInstance(String serverName,String ip, int port, String clusterName, String groupName, int weight ) throws NacosException {
        Instance instance = new Instance();
        instance.setEnabled(true);
        instance.setIp(ip);
        instance.setPort(port);
        instance.setWeight(weight==0?1:weight);
        instance.setClusterName(clusterName);
        instance.setEphemeral(true);
        instance.setServiceName(serverName);
        instance.setHealthy(true);
        Map<String, String> meta = new HashMap<>();
        meta.put("preserved.heart.beat.interval", "1000");
        meta.put("preserved.heart.beat.timeout", "3000");
        instance.setMetadata(meta);
        namingService.registerInstance(serverName, groupName, instance);
        FishRPCLog.info("注册nocas服务[%s][%s][%s]", serverName, groupName, instance);
    }


   public static void registerNacosService(boolean nacosEnable,String nacosServerName, String host, int port) throws NacosException {
        if(nacosEnable){
            String nacosGroupName = FishRPCConfig.getStringValue("nacos.server.group","DEFAULT_GROUP");
            String nacosClusterName = FishRPCConfig.getStringValue("nacos.server.cluster","defaultcluster");
            int weight = FishRPCConfig.getIntValue("nacos.server.weight", 1);
            registerInstance(nacosServerName,host,port,nacosClusterName,nacosGroupName,weight);
        }
    }

    public static List<Instance> findInstances(String serverName, String groupName , List<String> clusters) throws NacosException {
        namingService.subscribe(serverName, groupName, clusters, new EventListener() {
            @Override
            public void onEvent(Event event) {
                FishRPCLog.info("nacos subscribe {}", event);
            }
        });
        List<Instance> instances = namingService.selectInstances(serverName,groupName,clusters,true,true);
        for(Instance instance : instances){
            FishRPCLog.info("发现nocas服务[%s][%s][%s]", serverName, groupName, instance);
        }
        return instances;
    }

}
