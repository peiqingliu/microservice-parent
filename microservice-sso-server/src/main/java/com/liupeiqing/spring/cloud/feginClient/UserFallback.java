package com.liupeiqing.spring.cloud.feginClient;

import com.liupeiqing.spring.cloud.authbeans.AuthUser;
import com.liupeiqing.spring.cloud.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liupeiqing
 * @data 2018/9/4 15:52
 */
@Slf4j
@Service
public class UserFallback implements UserFeignClient {
    //private static final Logger log = LoggerFactory.getLogger(UserFallback.class);
    @Override
    public R<AuthUser> findUserByusername(String username) {
        log.error("调用异常:{}", "findUserByUsername", username);
        AuthUser user = new AuthUser();
        user.setUserId(-1);
        user.setUsername("default username");
        user.setStatu(-1);
        R<AuthUser> r = new R<>();
        r.setCode(-1);
        r.setData(user);
        r.setMsg("调用异常");
        return r;
    }

}
