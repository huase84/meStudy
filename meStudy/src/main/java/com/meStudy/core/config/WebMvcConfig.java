package com.meStudy.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** @author gongym */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3000)
public class WebMvcConfig implements WebMvcConfigurer {

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
      new UrlBasedCorsConfigurationSource();
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    /* 是否允许请求带有验证信息 */
    corsConfiguration.setAllowCredentials(true);
    /* 允许访问的客户端域名 */
    // spring-boot 2.4.0之前
//    corsConfiguration.addAllowedOrigin("*");
    // spring-boot 2.4.0之后
    corsConfiguration.addAllowedOriginPattern("*");
    /* 允许服务端访问的客户端请求头 */
    corsConfiguration.addAllowedHeader("*");
    /* 允许访问的方法名,GET POST等 */
    corsConfiguration.addAllowedMethod("*");
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    return new CorsFilter(urlBasedCorsConfigurationSource);
  }
}
