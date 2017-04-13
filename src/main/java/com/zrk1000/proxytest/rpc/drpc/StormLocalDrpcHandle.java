package com.zrk1000.proxytest.rpc.drpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zrk1000.proxytest.bolt.DispatchBolt;
import com.zrk1000.proxytest.proxy.ServiceMethod;
import com.zrk1000.proxytest.rpc.RpcHandle;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.drpc.DRPCSpout;
import org.apache.storm.drpc.ReturnResults;
import org.apache.storm.topology.TopologyBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by rongkang on 2017-04-02.
 */
@Profile("dev")
@Component("stormLocalDrpcHandle")
public class StormLocalDrpcHandle implements RpcHandle {

    private LocalDRPC drpc ;

    private String drpcService ;

    public StormLocalDrpcHandle() {
        this.drpc = new LocalDRPC();
        this.drpcService = "drpcService";

        TopologyBuilder builder = new TopologyBuilder();
        Config conf = new Config();
        conf.setNumWorkers(2);
        conf.setDebug(true);
//        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        DRPCSpout drpcSpout = new DRPCSpout(this.drpcService, drpc);
        builder.setSpout("drpcSpout", drpcSpout, 1);
        builder.setBolt("dispatch", new DispatchBolt(),1) .shuffleGrouping("drpcSpout");
        builder.setBolt("return", new ReturnResults(), 1).shuffleGrouping("dispatch");

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("local_cluster", conf, builder.createTopology());
    }

    public Object exec(ServiceMethod serviceMethod, Object[] args) {
        DrpcResponse drpcResponse = new DrpcResponse();
        String result = null;
        try{
            result = drpc.execute(this.drpcService, new DrpcRequest(serviceMethod.getClazz(),serviceMethod.getMethodName(),serviceMethod.hashCode(),args).toJSONString());
        }catch (Exception e){
            e.printStackTrace();
            drpcResponse.setCode(500);
            drpcResponse.setMsg(e.getMessage());
        }
        if(result!=null)
            drpcResponse = JSON.parseObject(result, DrpcResponse.class);

        return JSONObject.parseObject(JSON.toJSONString(drpcResponse.getData()), serviceMethod.getReturnType());
    }

    @Override
    public void close() throws IOException {
        System.out.println("==============do close!");
    }
}
