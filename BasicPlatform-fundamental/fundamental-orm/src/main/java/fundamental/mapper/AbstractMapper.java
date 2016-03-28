package fundamental.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yangyang
 * Date: 14-11-1
 * Time: 上午10:21
 * To change this template use File | Settings | File Templates.
 */
public interface AbstractMapper<T> {
    void add(T t);
    int update(T t);
    int delete(T t);
    T get(T t);
    List<T> find(Map<String, Object> conditions);
}
