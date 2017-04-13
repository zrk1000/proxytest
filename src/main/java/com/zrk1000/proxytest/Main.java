package com.zrk1000.proxytest;

import com.zrk1000.proxytest.bolt.DispatchBolt;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.drpc.DRPCSpout;
import org.apache.storm.drpc.ReturnResults;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Created by zrk-PC on 2017/4/13.
 */
public class Main {

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        Config config = new Config();
        DRPCSpout drpcSpout = new DRPCSpout(args[0]);
        builder.setSpout("drpcSpout", drpcSpout, 1);
        builder.setBolt("dispatch", new DispatchBolt(),1) .shuffleGrouping("drpcSpout");
        builder.setBolt("return", new ReturnResults(), 1).shuffleGrouping("dispatch");
        try {
            StormSubmitter.submitTopologyWithProgressBar(args[0], config, builder.createTopology());
        } catch (AlreadyAliveException e) {
            e.printStackTrace();
        } catch (InvalidTopologyException e) {
            e.printStackTrace();
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }

    }
}
