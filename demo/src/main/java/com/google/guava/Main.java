package main.java.com.google.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ...
 *
 * @author youke.xch
 * @since ${DATE}
 */
public class Main {

    /**
     * 本地内存
     */
    private static final LoadingCache<Long, String> CACHE = CacheBuilder.newBuilder().
            //30min过期
                    expireAfterWrite(30, TimeUnit.MINUTES).
            //最大保存10个值
                    maximumSize(10).
            build(new CacheLoader<Long, String>() {
                //不存在时缓存这个值
                @Override
                public String load(Long s) throws Exception {
                    return one(s);
                }
            });


    public static List<String> getAllCache(List<Long> idList) {
        List<String> cacheResult = new ArrayList<>();
        for (Long id : idList) {
            String value = CACHE.getUnchecked(id);
            cacheResult.add(value);
        }
        return cacheResult;
    }

    /**
     * 模拟调用
     *
     * @param id
     * @return
     */

    private static String one(Long id) {
        System.out.println("调用one");
        try {
            //模拟查询DB耗时
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "test_" + id;
    }

    public static void main(String[] args) {
        getAllCache(Arrays.asList(1L, 2L));
    }
}