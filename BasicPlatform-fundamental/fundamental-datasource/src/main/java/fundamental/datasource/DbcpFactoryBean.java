package fundamental.datasource;

import fundamental.config.FundamentalConfigProvider;
import fundamental.logger.FundamentalLogger;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: YangRichard
 * Date: 14-10-26
 * Time: 下午8:10
 * To change this template use File | Settings | File Templates.
 */
public class DbcpFactoryBean implements FactoryBean<BasicDataSource>,DisposableBean,InitializingBean {
    private BasicDataSource ds;
    private Properties properties;
    private String dbname;
    private static final FundamentalLogger LOGGER = FundamentalLogger.getLogger(DbcpFactoryBean.class);

    public BasicDataSource getDs() {
        return ds;
    }

    public void setDs(BasicDataSource ds) {
        this.ds = ds;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    //@Override
    public BasicDataSource getObject() throws Exception {
        ds = new BasicDataSource();
        this.tryToSetProperties();
        return ds;
    }

    //@Override
    public Class<?> getObjectType() {
        return BasicDataSource.class;
    }

    //@Override
    public boolean isSingleton() {
        return true;
    }

    //@Override
    public void destroy() throws Exception {
        if (ds == null) {
            return;
        }
        try {
            ds.close();
            ds = null;
        } catch (Exception e) {
            LOGGER.warn("close dbcp error", e);
        }
    }

    //@Override
    public void afterPropertiesSet() throws Exception {
        setProperties(FundamentalConfigProvider.getProp());
    }

    protected void tryToSetProperties() {
        String configPrefix = "dbcp.";
        if (dbname != null && dbname.trim().length() > 0) {
            configPrefix = configPrefix + dbname + ".";
        }

        if (properties == null) {
            throw new IllegalArgumentException("there is no dbcp properties setting");
        }
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();

            if (!key.startsWith(configPrefix)) {
                continue;
            }

            String propertyName = key.substring(configPrefix.length());
            try {
                tryToSetProperty(propertyName, value);
            } catch (Exception ex) {
                LOGGER.info("error to set property : key : " + key
                        + ", value : " + value, ex);
            }
        }
    }

    protected void tryToSetProperty(String propertyName,String propertyValue) throws Exception {
        String setterName = "set" + propertyName.substring(0, 1).toUpperCase(Locale.CHINA)
                + propertyName.substring(1);
        Method[] methods = BasicDataSource.class.getMethods();
        for (Method method : methods) {
            if (!method.getName().equals(setterName)) {
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 1) {
                LOGGER.debug("match method : {}, {}", new Object[] { method,
                        propertyValue });
                Class<?> parameterType = parameterTypes[0];
                this.invokeSetValue(method, parameterType, propertyValue);
            }
        }
    }

    private void invokeSetValue(Method method,Class<?> parameterType,String propertyValue) throws Exception{
        LOGGER.info("name :"+method.getName()+" value :"+propertyValue);
        if (parameterType == String.class) {
            method.invoke(ds, propertyValue);
        } else if (parameterType == Integer.class || parameterType == int.class) {
            method.invoke(ds, Integer.parseInt(propertyValue));
        } else if (parameterType == Long.class || parameterType == long.class) {
            method.invoke(ds, Long.parseLong(propertyValue));
        } else if (parameterType == Boolean.class
                || parameterType == boolean.class) {
            method.invoke(ds, Boolean.valueOf(propertyValue));
        } else {
            LOGGER.info("cannot process parameterType : [" + parameterType
                    + "]");
        }
    }
}
