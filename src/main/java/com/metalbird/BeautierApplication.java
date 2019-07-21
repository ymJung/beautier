package com.metalbird;

import com.metalbird.beautier.util.StaticValues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@SpringBootApplication
@EnableScheduling
@Slf4j
public class BeautierApplication {
	public static void main(String[] args) {
		SpringApplication.run(BeautierApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setReadTimeout(3000);
		requestFactory.setConnectTimeout(3000);
		return new RestTemplate(requestFactory);
	}

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager(StaticValues.CACHE_NAME);
	}

	@CacheEvict(allEntries = true, value = {StaticValues.CACHE_NAME})
	@Scheduled(fixedDelay = 10 * 60 * 1000 ,  initialDelay = 500) // 10분에 한번씩
	public void reportCacheEvict() {
	    log.info("flush cache.");
	}
}
