package com.javarush.test.level33.lesson15.big01.strategies;

import com.google.common.collect.HashBiMap;

public class HashBiMapStorageStrategy implements StorageStrategy {
    private HashBiMap<Long, String> data = HashBiMap.create();


    @Override
    public boolean containsKey(Long key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(String value) {
        return data.containsValue(value);
    }

    @Override
    public void put(Long key, String value) {
        data.put(key, value);

    }

    @Override
    public Long getKey(String value) {
        if (containsValue(value))
            return data.inverse().get(value);
        else
            return null;
    }

    @Override
    public String getValue(Long key) {
        if (containsKey(key))
            return data.get(key);
        else
            return null;
    }
}
