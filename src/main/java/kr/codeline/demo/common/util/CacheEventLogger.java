package kr.codeline.demo.common.util;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheEventLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent<? extends Object, ? extends Object> e) {
        log.debug("[CACHE {}] : {} : {}", e.getType(), e.getKey(), e.getNewValue());
    }
}