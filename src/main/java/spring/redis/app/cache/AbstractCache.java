package spring.redis.app.cache;

import java.util.List;

public interface AbstractCache<V, K> {

    public V getById(K id);

    public void add(V value);

    public boolean delete(K id);

    public boolean update(K id, V value);

    public List<V> findAll();

    public void clear();
}
