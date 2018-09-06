package com.liupeiqing.spring.cloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author liupeiqing
 * @data 2018/8/13 18:57
 * 定时任务类
 */
@Slf4j
@Component
public class ScheduledService {

    /**
     * fixedRate：定义一个按一定频率执行的定时任务
     * fixedDelay：定义一个按一定频率执行的定时任务，与上面不同的是，改属性可以配合initialDelay， 定义该任务延迟执行时间。
     * cron：通过表达式来配置任务执行时间
     * 一个cron表达式有至少6个（也可能7个）年份一般省略 有空格分隔的时间元素。按顺序依次为：
     *
     * 秒（0~59）
     * 分钟（0~59）
     * 3 小时（0~23）
     * 4 天（0~31）
     * 5 月（0~11）
     * 6 星期（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
     * 年份（1970－2099）
     *
     * “*”字符代表所有可能的值
     * “/”字符用来指定数值的增量
     *
     * 例如：在子表达式（分钟）里的“0/15”表示从第0分钟开始，每15分钟
     * 在子表达式（分钟）里的“3/20”表示从第3分钟开始，每20分钟（它和“3，23，43”）的含义一样
     *
     * “？”字符仅被用于天（月）和天（星期）两个子表达式，表示不指定值
     * 当2个子表达式其中之一被指定了值以后，为了避免冲突，需要将另一个子表达式的值设为“？
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void scheduled(){
        log.info("=====>>>>>scheduled", System.currentTimeMillis());
        Long time = System.currentTimeMillis();
        System.out.print("数据库操作! time" + time);
    }
}
