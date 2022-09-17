package com.youqiancheng.util;

import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionTools {

    public static <K, V> List<V> collectMulti(Map<K, V> map, Collection<K> list){
        if (CollectionUtils.isEmpty(list)){
            return Lists.newArrayList();
        }
        return list.stream().map(k->map.get(k)).collect(Collectors.toList());
    }

    public static <T, R> List<R> collectList(Collection<T> list, Function<T, R> func) {
        if (CollectionUtils.isEmpty(list)){
            return Lists.newArrayList();
        }
        return list.stream().filter(Objects::nonNull).map(func).collect(Collectors.toList());
    }

    public static <T, R> Set<R> collectSet(Collection<T> list, Function<T, R> func) {
        if (CollectionUtils.isEmpty(list)){
            return Sets.newHashSet();
        }
        return list.stream().filter(Objects::nonNull).map(func).collect(Collectors.toSet());
    }

    public static <T, R> Map<R, T> collectMap(Collection<T> list, Function<T, R> func) {
        if (CollectionUtils.isEmpty(list)){
            return Maps.newHashMap();
        }
        return list.stream().filter(Objects::nonNull).distinct().collect(Collectors.toMap(func, v -> v));
    }

    public static <T> T getAny(Collection<T> collection){
        if (CollectionUtils.isEmpty(collection)){
            return null;
        }
        return collection.stream().findAny().orElse(null);
    }


    public static <T> T getAny(Collection<T> collection, Predicate<T> predicate){
        if (CollectionUtils.isEmpty(collection)){
            return null;
        }
        return collection.stream().filter(predicate).findAny().orElse(null);
    }

    public static <T> List<T> deduplicate(Collection<T> collection){
        HashSet<T> ts = new HashSet<>(collection);
        return Lists.newArrayList(ts);
    }

}
