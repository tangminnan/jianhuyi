package com.jianhuyi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.jianhuyi.*.dao")
@SpringBootApplication
public class BootdoApplication extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(BootdoApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(BootdoApplication.class, args);
    System.out.println(
        "ヾ(◍°∇°◍)ﾉﾞ    bootdo启动成功      ヾ(◍°∇°◍)ﾉﾞ\n"
            + " ______                    _   ______            \n"
            + "|_   _ \\                  / |_|_   _ `.          \n"
            + "  | |_) |   .--.    .--. `| |-' | | `. \\  .--.   \n"
            + "  |  __'. / .'`\\ \\/ .'`\\ \\| |   | |  | |/ .'`\\ \\ \n"
            + " _| |__) || \\__. || \\__. || |, _| |_.' /| \\__. | \n"
            + "|_______/  '.__.'  '.__.' \\__/|______.'  '.__.'  ");
  }
  /*
  @Bean
  public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate redisTemplate = new RedisTemplate();
    redisTemplate.setConnectionFactory(redisConnectionFactory);

    // 使用Jackson2JsonRedisSerialize 替换默认序列化
    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
        new Jackson2JsonRedisSerializer(Object.class);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

    jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  @Bean // 必须new 一个RestTemplate并放入spring容器当中,否则启动时报错
  public RestTemplate restTemplate() {
    HttpComponentsClientHttpRequestFactory httpRequestFactory =
        new HttpComponentsClientHttpRequestFactory();

    httpRequestFactory.setConnectionRequestTimeout(30 * 1000);
    httpRequestFactory.setConnectTimeout(30 * 3000);
    httpRequestFactory.setReadTimeout(60 * 10000);
    return new RestTemplate(httpRequestFactory);
  }*/
}
