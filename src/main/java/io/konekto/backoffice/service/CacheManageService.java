package io.konekto.backoffice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class CacheManageService {

    @Autowired
    CacheManager cacheManager;

    private void evictAllCaches() {
        cacheManager.getCacheNames()
            .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void evictAllCachesAtIntervals() {
        evictAllCaches();
    }

}
