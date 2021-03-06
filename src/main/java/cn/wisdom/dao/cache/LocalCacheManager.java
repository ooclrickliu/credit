/**
 * LocalCacheManager.java
 * 
 * Copyright@2015 OVT Inc. All rights reserved. 
 * 
 * May 27, 2015
 */
package cn.wisdom.dao.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import cn.wisdom.common.log.Logger;
import cn.wisdom.common.log.LoggerFactory;
import cn.wisdom.dao.constant.LoggerConstants;

/**
 * LocalCacheManager
 * 
 * @Author zhi.liu
 * @Version 1.0
 * @See
 * @Since [OVT Cloud Platform]/[DAO] 1.0
 */
@Service
public class LocalCacheManager
{

    private Map<String, Cache> cacheMap =
            new ConcurrentHashMap<String, Cache>();
    
    protected Logger logger = LoggerFactory.getLogger(LoggerConstants.LOCAL_CACHE_LOGGER);

    @SuppressWarnings("unchecked")
    public <T extends Cache> T getCache(String cacheName)
    {
        return (T) cacheMap.get(cacheName);
    }

    public void registerCache(String name, Cache cache)
    {
        cacheMap.put(name, cache);
    }

}
