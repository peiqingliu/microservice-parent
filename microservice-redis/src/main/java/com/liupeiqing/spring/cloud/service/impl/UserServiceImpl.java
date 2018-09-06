package com.liupeiqing.spring.cloud.service.impl;

import com.liupeiqing.spring.cloud.cache.AdminCacheKey;
import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.repository.UserRepository;
import com.liupeiqing.spring.cloud.service.UserService;
import com.liupeiqing.spring.cloud.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * @author liupeiqing
 * @data 2018/7/23 19:22
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisUtil redisUtil;

    //读写锁
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    @Cacheable(value = AdminCacheKey.USER_INFO,key = " 'user_'+ #userId")
    public User findByUserId(Long userId) {
        User user =  (User) this.userRepository.getOne(userId);
        return user;
    }

    /**
     * @Cacheable
     *      1   @Cacheable可以标记在一个方法上，也可以标记在一个类上。
     *        当标记在一个方法上时表示该方法是支持缓存的，当标记在一个类上时则表示该类所有的方法都是支持缓存的。
     *        对于一个支持缓存的方法，Spring会在其被调用后将其返回值缓存起来，以保证下次利用同样的参数来执行该方法时可以直接从缓存中获取结果，
     *        而不需要再次执行该方法。Spring在缓存方法的返回值时是以键值对进行缓存的，值就是方法的返回结果，
     *        至于键的话，Spring又支持两种策略，默认策略和自定义策略，这个稍后会进行说明。
     *        需要注意的是当一个支持缓存的方法在对象内部被调用时是不会触发缓存功能的。
     *
     *        对于使用@Cacheable标注的方法，Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，
     *        如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。
     *
     *        @Cacheable可以指定三个属性，value、key和condition。
     *      1.1  value属性指定Cache名称
     *        value属性是必须指定的，其表示当前方法的返回值是会被缓存在哪个Cache上的，对应Cache的名称。
     *        其可以是一个Cache也可以是多个Cache，当需要指定多个Cache时其是一个数组。
     *         @Cacheable("cache1")//Cache是发生在cache1上的
     *
     *           public User find(Integer id) {
     *
     *               returnnull;
     *
     *              }
     *
     *      1.2  key属性是用来指定Spring缓存方法的返回结果时对应的key的。该属性支持SpringEL表达式。
     *          当我们没有指定该属性时，Spring将使用默认策略生成key。我们这里先来看看自定义策略，至于默认策略会在后文单独介绍。
     *
     *        自定义策略是指我们可以通过Spring的EL表达式来指定我们的key。
     *        这里的EL表达式可以使用方法参数及它们对应的属性。使用方法参数时我们可以直接使用“#参数名”或者“#p参数index”。下面是几个使用参数作为key的示例。
     *
     *    @Cacheable(value="users", key="#id")
     *
     *    public User find(Integer id) {
     *
     *       returnnull;
     *
     *    }
     *
     *
     *
     *    @Cacheable(value="users", key="#p0")
     *
     *    public User find(Integer id) {
     *
     *       returnnull;
     *
     *    }
     *
     *
     *
     *    @Cacheable(value="users", key="#user.id")
     *
     *    public User find(User user) {
     *
     *       returnnull;
     *
     *    }
     *
     *
     *
     *    @Cacheable(value="users", key="#p0.id")
     *
     *    public User find(User user) {
     *
     *       returnnull;
     *
     *    }
     *
     *    1.1.3  condition属性指定发生的条件
     *        有的时候我们可能并不希望缓存一个方法所有的返回结果。通过condition属性可以实现这一功能。
     *        condition属性默认为空，表示将缓存所有的调用情形。
     *        其值是通过SpringEL表达式来指定的，当为true时表示进行缓存处理；当为false时表示不进行缓存处理，
     *        即每次调用该方法时该方法都会执行一次。如下示例表示只有当user的id为偶数时才会进行缓存。
     *
     *    @Cacheable(value={"users"}, key="#user.id", condition="#user.id%2==0")
     *
     *    public User find(User user) {
     *
     *       System.out.println("find user by user " + user);
     *
     *       return user;
     *
     *    }
     *
     *
     *    2
     *    @CachePut也可以声明一个方法支持缓存功能。
     *    与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，
     *    而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     *
     *    1.3     @CacheEvict
     *        @CacheEvict是用来标注在需要清除缓存元素的方法或类上的。当标记在一个类上时表示其中所有的方法的执行都会触发缓存的清除操作。
     *        @CacheEvict可以指定的属性有value、key、condition、allEntries和beforeInvocation。
     *        其中value、key和condition的语义与@Cacheable对应的属性类似。即value表示清除操作是发生在哪些Cache上的（对应Cache的名称）；
     *        key表示需要清除的是哪个key，如未指定则会使用默认策略生成的key；condition表示清除操作发生的条件。
     *        下面我们来介绍一下新出现的两个属性allEntries和beforeInvocation。
     * @return
     *
     *  1. 重入方面其内部的WriteLock可以获取ReadLock,但是反过来ReadLock想要获得WriteLock则永远都不要想.
     *        2. WriteLock可以降级为ReadLock,顺序是:先获得WriteLock再获得ReadLock,然后释放WriteLock,
     *        这时候线程将保持Readlock的持有.反过来ReadLock想要升级为WriteLock则不可能.
     *        3. ReadLock可以被多个线程持有并且在作用时排斥任何的WriteLock,而WriteLock则是完全的互斥.
     *        这一特性最为重要,因为对于高读取频率而相对较低写入的数据结构,使用此类锁同步机制则可以提高并发量.
     */
    @Override
    //@Cacheable(value = AdminCacheKey.USER_INFO, key = "allUser")
    public List<User> findAllUser() {
        //先加锁
        readWriteLock.readLock().lock();
        List<User> users = new ArrayList<User>();
        try {
            Long size = redisUtil.lGetListSize("allUser");
            if (size != null && size > 0){  //缓存中有值
                users = (List<User>) redisUtil.lGet("allUser",0,-1);
            }else{  //  缓存中没有
                readWriteLock.readLock().unlock();  //先释放掉读锁
                readWriteLock.writeLock().lock();  //获得写锁
                try{
                     //去数据库取数据,再判断一次是否为null，因为有可能多个线程获得写锁，现活的写锁的把users赋值了
                    if(users.size() == 0){
                        users = userRepository.findAll();
                        //放入缓存
                        redisUtil.lSet("allUser",users,1 * 60 * 1000);
                    }
                }catch (Exception e){
                    e.printStackTrace();

                }finally {
                    //先上读锁，然后再解除写锁（这样可以成功完成，在解除写锁前获得读锁，写锁被降级--这翻译的api上的）
                    //此时在写锁的内部 是可以获得读锁的，但是在读锁的内部 永远不能获得写锁，就上上面 要先释放  在获取
                    readWriteLock.readLock().lock();
                    readWriteLock.writeLock().unlock();//解除写锁，读锁仍然持有
                }


            }
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            readWriteLock.readLock().unlock();  //释放掉读锁

        }
        return users;
    }

    @Transactional  //事务回滚
    @Override
    @CacheEvict(value = { AdminCacheKey.USER_INFO }, allEntries = true)  //清除缓存
    public boolean delUser(Long userId) {
        if(null == userId) return false;
        this.userRepository.delete(userId);
        return true;
    }

}
