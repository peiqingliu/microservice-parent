package com.liupeiqing.spring.cloud.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author liupeiqing
 * @data 2018/8/16 19:46
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public String listTasks(){
        return "任务列表!";
    }

    /**
     * @PostAuthorize 注解使用并不多，在方法执行后再进行权限验证。
     *
     * 所以它适合验证带有返回值的权限。
     * Spring EL 提供 返回对象能够在表达式语言中获取返回的对象returnObject
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")  //近入方法前的权限验证  只有拥有ADMIN权限的用户调用
    public String newTask(){
        return "创建了一个新任务!";
    }

    @PutMapping("/{taskId}")
    public String updateTask(@PathVariable("taskId") Integer taskId){
        return "更新了一个id为"+taskId+"任务!";
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN') AND hasRole('USER')")  //必须拥有ADMIN和USER 的人 才能删除
    public String deleteTask(@PathVariable Integer taskId){
        return "删除了一个id为"+taskId+"的任务!";
    }
}
