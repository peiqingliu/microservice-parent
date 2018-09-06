package com.liupeiqing.spring.cloud.redisLock;

import com.liupeiqing.spring.cloud.util.RedisToolsConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;

/**
 * @author liupeiqing
 * @data 2018/9/1 14:07
 * <p>
 * 分布式锁
 * <p>
 * 分布式了之后系统由以前的单进程多线程的程序变为了多进程多线程，传统的多线程就不适用。
 * <p>
 * 因此业界常用的解决方案通常是借助于一个第三方组件并利用它自身的排他性来达到多进程的互斥。如：
 * <p>
 * 基于 DB 的唯一索引。
 * 基于 ZK 的临时有序节点。
 * 基于 Redis 的 NX EX 参数。
 * 这里主要基于 Redis 进行讨论。
 * <p>
 * 实现
 * 既然是选用了 Redis，那么它就得具有排他性才行。同时它最好也有锁的一些基本特性：
 * <p>
 * 高性能(加、解锁时高性能)
 * 可以使用阻塞锁与非阻塞锁。
 * 不能出现死锁。
 * 可用性(不能出现节点 down 掉后加锁失败)。
 * 这里利用 Redis set key 时的一个 NX 参数可以保证在这个 key 不存在的情况下写入成功。并且再加上 EX 参数可以让该 key 在超时之后自动删除。
 * <p>
 * 所以利用以上两个特性可以保证在同一时刻只会有一个进程获得锁，并且不会出现死锁(最坏的情况就是超时自动删除 key)。
 */
@Slf4j
@Component
public class RedisLock {


    private static final String LOCK_MSG = "OK";
    private static final Long UNLOCK_MSG = 1L;
    //即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
    private static final String SET_IF_NOT_EXIST = "NX";
    //我们要给这个key加一个过期的设置，具体时间由第五个参数决定。
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    //与第四个参数相呼应，代表key的过期时间。
    private static final int TIME = 1000;
    //加锁前缀
    private String lockPrefix = "lock_";

    //默认沉睡时间
    private int sleepTime = 1000;


    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    //此处默认单机版本
    private int type = 1;

    public RedisLock(JedisConnectionFactory jedisConnectionFactory){
        this.jedisConnectionFactory = jedisConnectionFactory;
    }

    public Object getConnection() {
        Object connection;
        if (type == RedisToolsConstant.SINGLE) {
            RedisConnection redisConnection = jedisConnectionFactory.getConnection();
            connection = redisConnection.getNativeConnection();
        } else {
            RedisClusterConnection clusterConnection = jedisConnectionFactory.getClusterConnection();
            connection = clusterConnection.getNativeConnection();
        }
        return connection;
    }

    /**
     * 尝试获取分布式锁
     *
     * @param key       锁  因为key是唯一的
     * @param requestId 请求标识 需要请求表示的原因
     *                  如果线程A获取了锁，并设置了超时时间，但是由于执行周期较长，到了超时时间，锁会自动释放。
     *                  此时线程B获取了锁，但是执行很快，B释放锁，此时会出现线程B释放的是线程A的锁。
     *                  所以最好的方式是在每次解锁时都需要判断锁是否是自己的。
     *                  这时就需要结合加锁机制一起实现了。
     *                  加锁时需要传递一个参数，将该参数作为这个 key 的 value，这样每次解锁时判断 value 是否相等即可。
     *                  requestId可以使用UUID.randomUUID().toString()方法生成
     * @return 我们的加锁代码满足我们可靠性里描述的三个条件。
     * 首先，set()加入了NX参数，可以保证如果已有key存在，则函数不会调用成功，也就是只有一个客户端能持有锁，满足互斥性。
     * 其次，由于我们对锁设置了过期时间，即使锁的持有者后续发生崩溃而没有解锁，锁也会因为到了过期时间而自动解锁（即key被删除），不会发生死锁。
     * 最后，因为我们将value赋值为requestId，代表加锁的客户端请求标识，那么在客户端在解锁的时候就可以进行校验是否是同一个客户端。
     * 由于我们只考虑Redis单机部署的场景，所以容错性我们暂不考虑。
     */
    public boolean tryLock(String key, String requestId) {
        //get connection
        Object connection = getConnection();
        String result;
        if (connection instanceof Jedis) {  //单机版本
            result = ((Jedis) connection).set(lockPrefix + key, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, TIME * 10);
            ((Jedis) connection).close();
        } else {  //集群版本
            result = ((JedisCluster) connection).set(lockPrefix + key, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, TIME * 10);
        }
        log.info("获取锁的结果result" + result);
        if (result.equals(LOCK_MSG)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 效果同上 自定义失效时间
     * Non-blocking lock
     *
     * @param key        lock business type
     * @param request    value
     * @param expireTime custom expireTime
     * @return true lock success
     * false lock fail
     */
    public boolean tryLock(String key, String request, int expireTime) {
        //get connection
        Object connection = getConnection();
        String result;

        if (connection instanceof Jedis) {
            result = ((Jedis) connection).set(lockPrefix+key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            ((Jedis) connection).close();
        } else {
            result = ((JedisCluster) connection).set(lockPrefix+key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        }

        if (LOCK_MSG.equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    //错误示例1
    //比较常见的错误示例就是使用jedis.setnx()和jedis.expire()组合实现加锁，代码如下：

    public static void wrongGetLock1(Jedis jedis, String lockKey, String requestId, int expireTime) {

        /**
         * setnx()方法作用就是SET IF NOT EXIST，
         * expire()方法就是给锁加一个过期时间。乍一看好像和前面的set()方法结果一样，
         * 然而由于这是两条Redis命令，不具有原子性，如果程序在执行完setnx()之后突然崩溃，导致锁没有设置过期时间。
         * 那么将会发生死锁。
         * 网上之所以有人这样实现，是因为低版本的jedis并不支持多参数的set()方法。
         */
        Long result = jedis.setnx(lockKey, requestId);
        if (result == 1) {
            // 若在这里程序突然崩溃，则无法设置过期时间，将发生死锁
            jedis.expire(lockKey, expireTime);
        }
    }

    //错误示例2
    //这一种错误示例就比较难以发现问题，而且实现也比较复杂。
    // 实现思路：使用jedis.setnx()命令实现加锁，其中key是锁，value是锁的过期时间。
    // 执行过程：1. 通过setnx()方法尝试加锁，如果当前锁不存在，返回加锁成功。
    // 2. 如果锁已经存在则获取锁的过期时间，和当前时间比较，如果锁已经过期，则设置新的过期时间，返回加锁成功。
    // 代码如下：

    //那么这段代码问题在哪里？
    // 1. 由于是客户端自己生成过期时间，所以需要强制要求分布式下每个客户端的时间必须同步。
    // 2. 当锁过期的时候，如果多个客户端同时执行jedis.getSet()方法，那么虽然最终只有一个客户端可以加锁，但是这个客户端的锁的过期时间可能被其他客户端覆盖。
    // 3. 锁不具备拥有者标识，即任何客户端都可以解锁。
    public static boolean wrongGetLock2(Jedis jedis, String lockKey, int expireTime) {

        long expires = System.currentTimeMillis() + expireTime;
        String expiresStr = String.valueOf(expires);

        // 如果当前锁不存在，返回加锁成功
        if (jedis.setnx(lockKey, expiresStr) == 1) {
            return true;
        }

        // 如果锁存在，获取锁的过期时间
        String currentValueStr = jedis.get(lockKey);
        if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
            // 锁已过期，获取上一个锁的过期时间，并设置现在锁的过期时间
            String oldValueStr = jedis.getSet(lockKey, expiresStr);
            if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                // 考虑多线程并发的情况，只有一个线程的设置值和当前值相同，它才有权利加锁
                return true;
            }
        }

        // 其他情况，一律返回加锁失败
        return false;

    }


    /**
     * blocking lock
     * 获取阻塞锁
     *
     * @param key
     * @param request
     */
    public void lock(String key, String request) throws InterruptedException {
        //get connection
        Object connection = getConnection();
        String result;
        //无限循环，除非遇到结束语句，结束循环
        for (; ; ) {
            if (connection instanceof Jedis) {
                result = ((Jedis) connection).set(key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
                if (LOCK_MSG.equals(result)) {
                    ((Jedis) connection).close();
                }
            } else {
                result = ((JedisCluster) connection).set(key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
            }

            if (LOCK_MSG.equals(result)) {
                break;
            }

            //防止一直消耗 CPU
            Thread.sleep(sleepTime);
        }

    }

    /**
     * blocking lock,custom time
     * 自定义阻塞时间
     *
     * @param key
     * @param request
     * @param blockTime custom time
     * @return
     * @throws InterruptedException
     */
    public boolean lock(String key, String request, int blockTime) throws InterruptedException {

        //get connection
        Object connection = getConnection();
        String result;
        while (blockTime >= 0) {
            if (connection instanceof Jedis) {
                result = ((Jedis) connection).set(key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
                if (LOCK_MSG.equals(result)) {
                    ((Jedis) connection).close();
                }
            } else {
                result = ((JedisCluster) connection).set(key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * TIME);
            }
            if (LOCK_MSG.equals(result)) {
                return true;
            }
            blockTime -= sleepTime;

            Thread.sleep(sleepTime);
        }
        return false;
    }


    /**
     * 释放分布式锁
     *
     * @param key       锁
     * @param requestId 请求标识
     * @return 第一行代码，我们写了一个简单的Lua脚本代码，
     * <p>
     * 第二行代码，我们将Lua代码传到jedis.eval()方法里，并使参数KEYS[1]赋值为lockKey，ARGV[1]赋值为requestId。
     * eval()方法是将Lua代码交给Redis服务端执行。
     * <p>
     * 那么这段Lua代码的功能是什么呢？
     * 其实很简单，首先获取锁对应的value值，检查是否与requestId相等，如果相等则删除锁（解锁）。
     * 那么为什么要使用Lua语言来实现呢？因为要确保上述操作是原子性的
     */
    public boolean unlock(String key, String requestId) {

        //lua脚本
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        //获取连接
        Object connect = getConnection();
        Object result = null;
        if (connect instanceof Jedis) {
            result = ((Jedis) connect).eval(script, Collections.singletonList(lockPrefix + key), Collections.singletonList(requestId));
        } else if (connect instanceof JedisCluster) {
            result = ((JedisCluster) connect).eval(script, Collections.singletonList(lockPrefix + key), Collections.singletonList(requestId));
        } else {
            //throw new RuntimeException("instance is error") ;
            return false;
        }

        if (UNLOCK_MSG.equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    //错误示例1

    //最常见的解锁代码就是直接使用jedis.del()方法删除锁，
    // 这种不先判断锁的拥有者而直接解锁的方式，会导致任何客户端都可以随时进行解锁，即使这把锁不是它的。

    public static void wrongReleaseLock1(Jedis jedis, String lockKey) {
        jedis.del(lockKey);
    }

    //错误示例2
    // 这种解锁代码乍一看也是没问题，甚至我之前也差点这样实现，与正确姿势差不多，唯一区别的是分成两条命令去执行，代码如下：

    public static void wrongReleaseLock2(Jedis jedis, String lockKey, String requestId) {

        // 判断加锁与解锁是不是同一个客户端
        if (requestId.equals(jedis.get(lockKey))) {
            // 若在此时，这把锁突然不是这个客户端的，则会误解锁
            jedis.del(lockKey);
        }

    }
    // 如代码注释，问题在于如果调用jedis.del()方法的时候，
    // 这把锁已经不属于当前客户端的时候会解除他人加的锁。那么是否真的有这种场景？
    // 答案是肯定的，比如客户端A加锁，一段时间之后客户端A解锁，在执行jedis.del()之前，锁突然过期了，
    // 此时客户端B尝试加锁成功，然后客户端A再执行del()方法，则将客户端B的锁给解除了。

}
