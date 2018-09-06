package com.liupeiqing.spring.cloud.repository;

import com.liupeiqing.spring.cloud.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Integer> {

}
