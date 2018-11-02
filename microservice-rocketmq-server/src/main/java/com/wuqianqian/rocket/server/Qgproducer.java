package com.wuqianqian.rocket.server;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author liupeqing
 * @date 2018/10/26 15:33
 */
public class Qgproducer {

    //禁用
    public static String fUser = "{\"userId\":\"cefc905ce54a4246b9b323d70dc56e9cf\"," +
            "\"jobName\":\"大学老师\",\"realName\":\"高宾\"," +
            "\"schoolId\":\"0001\"," +
            "\"schoolName\":\"海康小学\"," +
            "\"status\":\"F\",\"identifier\":\"gaobin\"}";
    //新增
    public static String aUser = "{\"userId\":\"cefc905ce54a4246b9b323d70dc56e9ca\"," +
            "\"jobName\":\"副校长\",\"realName\":\"孙权\"," +
            "\"schoolId\":\"21a7d5f01b9311e82c37ef94160bc03d\"," +
            "\"schoolName\":\"海康小学\"," +
            "\"status\":\"N\",\"identifier\":\"sunquan\"}";

    //新增
    public static String aUser1 = "{\"userId\":\"cefc905ce54a4246b9b323d70dc56e9b\"," +
            "\"jobName\":\"大学老师\",\"realName\":\"gaobin\"," +
            "\"schoolId\":\"004\"," +
            "\"schoolName\":\"幸福小学\"," +
            "\"status\":\"N\",\"identifier\":\"gaobin\"}";

    //更新
    public static String uUser = "{\"userId\":\"cefc905ce54a4246b9b323d70dc56e9cu\"," +
            "\"jobName\":\"校委\",\"realName\":\"孙大权\"," +
            "\"schoolId\":\"21a7d5f01b9311e82c37ef94160bc03d\"," +
            "\"schoolName\":\"海康大学\"," +
            "\"status\":\"N\",\"identifier\":\"sunquan\"}";
    //删除
    public static String dUser = "{\"userId\":\"cefc905ce54a4246b9b323d70dc56e9cd\"," +
            "\"jobName\":\"校长\",\"realName\":\"洪晓明\"," +
            "\"schoolId\":\"21a7d5f01b9311e82c37ef94160bc03d\"," +
            "\"schoolName\":\"海康小学\"," +
            "\"status\":\"D\",\"identifier\":\"gaobin\"}";

    public static void main(String[] args) {

        DefaultMQProducer producer = new DefaultMQProducer("producer_group_name");

        producer.setNamesrvAddr("10.11.80.24:9876;10.11.80.111:9876");
        try {
            producer.start();
            Message messageA = new Message("base_info_qg_anfang","teacher_add",aUser.getBytes(RemotingHelper.DEFAULT_CHARSET));
            Message messageA1 = new Message("base_info_qg_anfang","teacher_add", aUser1.getBytes(RemotingHelper.DEFAULT_CHARSET));
            Message messageU = new Message("base_info_qg_anfang", "teacher_update",uUser.getBytes(RemotingHelper.DEFAULT_CHARSET));
            Message messageF = new Message("base_info_qg_anfang", fUser.getBytes(RemotingHelper.DEFAULT_CHARSET));
            Message messageD = new Message("base_info_qg_anfang", dUser.getBytes(RemotingHelper.DEFAULT_CHARSET));
           // Message[] messages = new Message[]{messageA,messageU,messageF,messageD};
            Message[] messages = new Message[]{messageD};

            //发送消息
            for (Message message : messages) {
                SendResult result = producer.send(message);
                System.out.println("消息发送成功:id:" + result.getMsgId() +
                        " result:" + result.getSendStatus());
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } finally {
            //6.释放生产者
            producer.shutdown();
        }
    }



}
