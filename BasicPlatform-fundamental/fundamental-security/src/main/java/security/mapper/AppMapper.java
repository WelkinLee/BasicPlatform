package security.mapper;


import mapper.AbstractMapper;
import security.entity.App;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sunhui
 * Date: 14-5-12
 * Time: 上午9:29
 * To change this template use File | Settings | File Templates.
 */
public interface AppMapper extends AbstractMapper<App> {
    public List<App> findByCondition(Map<String, Object> map);
    public long getIdByName(String name);
    public String getNameById(long id);
}
