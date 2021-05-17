package io.konekto.backoffice.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@EnableCaching
@EnableScheduling
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        Cache businessTypes = new ConcurrentMapCache("businessTypes");
        Cache subBusinessTypes = new ConcurrentMapCache("subBusinessTypes");
        cacheManager.setCaches(Arrays.asList(
            businessTypes,
            subBusinessTypes
        ));
        return cacheManager;
    }

}
