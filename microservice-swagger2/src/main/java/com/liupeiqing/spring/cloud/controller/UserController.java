package com.liupeiqing.spring.cloud.controller;

import com.liupeiqing.spring.cloud.domain.User;
import com.liupeiqing.spring.cloud.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liupeiqing
 * @data 2018/8/20 11:25
 *
 * @Api：用在请求的类上，表示对类的说明
 *     tags="说明该类的作用，可以在UI界面上看到的注解"
 *     value="该参数没什么意义，在UI界面上也看到，所以不需要配置"
 *
 *@ApiOperation：用在请求的方法上，说明方法的用途、作用
 *     value="说明方法的用途、作用"
 *     notes="方法的备注说明"
 *
 * @ApiImplicitParams：用在请求的方法上，表示一组参数说明
 *     @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
 *         name：参数名
 *         value：参数的汉字说明、解释
 *         required：参数是否必须传
 *         paramType：参数放在哪个地方
 *             · header --> 请求参数的获取：@RequestHeader
 *             · query --> 请求参数的获取：@RequestParam
 *             · path（用于restful接口）--> 请求参数的获取：@PathVariable
 *             · body（不常用）
 *             · form（不常用）
 *         dataType：参数类型，默认String，其它值dataType="Integer"
 *         defaultValue：参数的默认值
 */
@Api(value="/user", tags="用户接口模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @ApiOperation(value = "获取用户信息",notes = "通过id获取用户信息")
    @ApiImplicitParam(name = "id",value = "用户ID",required = true,dataType = "Long")
    @GetMapping("/api/{id}")
    public User findUserById(@PathVariable("id") Long id) {
        User findOne = this.userRepository.findOne(id);
        return findOne;
    }

    @ApiOperation(value="获取用户列表", notes="")
    @GetMapping("/findAll")
    public List<User> findAll(){
        List<User> users = this.userRepository.findAll();
        return users;
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        User user1 = this.userRepository.save(user);
        return user1;
    }

    @PutMapping("/update/{id}")
    public User updateUser(@RequestBody User user){
       User user1 =  this.userRepository.save(user);
        return user1;
    }

    @ApiOperation(value="获取用户信息", notes="根据用户名或者年龄获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username", value="用户username", required=true, dataType="String"),
            @ApiImplicitParam(name="age", value="age", required=true, dataType="Integer")
    })
    @GetMapping("/{username}/{age}")
    public List<User> getUserByUsernameOrAge(@PathVariable("username") String username,@PathVariable("age") Integer age){
        List<User> users = this.userRepository.findByName2(username,age);
        return users;
    }
}
