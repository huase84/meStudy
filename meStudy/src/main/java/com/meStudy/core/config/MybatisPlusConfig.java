package com.meStudy.core.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author gongym */
@Configuration
@MapperScan(value = {"com.meStudy.api.**.mapper*"})
public class MybatisPlusConfig {

  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor().setLimit(-1);
  }
}
