package kr.codeline.demo.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 컨트롤러에서 @LoginUser 어노테이션 쓸때 파라메터가 Map 타입일 경우 Resolver 가 호출되지 않는다. Map 을 감싸는
 * 타입으로 하나 맹금 @LoginUser Data<String, String> loginUser
 * 
 * @author 김영명
 */
public class Data<K, V> {
    private Map<K, V> data = new HashMap<>();

    public Data() {
        data = new HashMap<>();
    }

    public Data(Map<K, V> data) {
        this.data.putAll(data);
    }

    public V get(K key) {
        return data.get(key);
    }

    public V put(K key, V value) {
        return this.data.put(key, value);
    }

    public Map<K, V> map() {
        return new HashMap<>(data);
    }

    public String toString() {
        return this.data.toString();
    }
}