/**
 * 
 */
package com.ibm.tivoli.cmcc.session;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author zhaodonglu
 * 
 */
public class EhcacheBasedSessionCache<V> implements SessionCache<V> {

  private CacheManager cacheManager = null;

  private String cacheName = null;

  /**
   * 
   */
  public EhcacheBasedSessionCache() {
    super();
  }

  /**
   * @return the cacheManager
   */
  public CacheManager getCacheManager() {
    return cacheManager;
  }

  /**
   * @param cacheManager the cacheManager to set
   */
  public void setCacheManager(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  /**
   * @return the cacheName
   */
  public String getCacheName() {
    return cacheName;
  }

  /**
   * @param cacheName the cacheName to set
   */
  public void setCacheName(String cacheName) {
    this.cacheName = cacheName;
  }

  /**
   * @return
   */
  private Cache getCache() {
    Cache cache = this.cacheManager.getCache(cacheName);
    return cache;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.cmcc.session.SessionCache#put(java.lang.String,
   * java.lang.Object)
   */
  public void put(String key, V value) {
    Cache cache = getCache();
    Element element = new Element(key, value);
    cache.put(element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.cmcc.session.SessionCache#get(java.lang.String)
   */
  public V get(String key) {
    Cache cache = getCache();
    Element element = cache.get(key);
    
    V value = (V) element.getValue();
    return value;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.cmcc.session.SessionCache#remove(java.lang.String)
   */
  public void remove(String key) {
    Cache cache = getCache();
    cache.remove(key);
  }

}
