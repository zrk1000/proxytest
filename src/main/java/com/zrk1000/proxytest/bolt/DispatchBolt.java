package com.zrk1000.proxytest.bolt;

import com.zrk1000.proxytest.Application;
import com.zrk1000.proxytest.service.BeanServiceImpl;
import com.zrk1000.proxytest.service.TestService;
import com.zrk1000.proxytest.service.TestServiceImpl;
import com.zrk1000.proxytest.spring.MyBeanDefinitionRegistryPostProcessor;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by rongkang on 2017-04-03.
 */
public class DispatchBolt extends BaseBasicBolt {

    ConfigurableApplicationContext applicationContext;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        try{
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.zrk1000.proxytest.service","com.zrk1000.proxytest.mybatis");
            TestServiceImpl beanService = (TestServiceImpl)applicationContext.getBean("testServiceImpl");
            beanService.say(null,null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {



        String param = tuple.getString(0);
//        applicationContext.getBean("")
        System.out.println("===========");
        String str = "{\"id\":1111,\"name\":\"tom\",\"age\":18}";

        collector.emit(new Values(str, tuple.getValue(1)));
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("result", "return-info"));
    }

    @Override
    public void cleanup() {
    }
}
