package config;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: YangRichard
 * Date: 14-10-26
 * Time: 下午8:19
 * To change this template use File | Settings | File Templates.
 */
public class ConfigProperties {
    private static Properties properties;

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        ConfigProperties.properties = properties;
    }

    protected static void setPropertiesStatic(Properties properties) {
        ConfigProperties.properties = properties;
    }
}
