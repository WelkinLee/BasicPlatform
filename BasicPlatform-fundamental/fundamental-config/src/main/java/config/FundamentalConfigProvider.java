package config;

import logger.FundamentalLogger;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: YangRichard
 * Date: 14-10-26
 * Time: 下午8:25
 * To change this template use File | Settings | File Templates.
 */
public class FundamentalConfigProvider {
    public static final String FILE_PREFIX = "file:";
    public static final String CLASSPATH_PREFIX = "classpath:";
    public static final String DEFAULT_CONFIG_PATH = "classpath:/fundamental.config.properties";
    private static final FundamentalLogger LOGGER = FundamentalLogger.getLogger(FundamentalConfigProvider.class);
    private static Properties prop = null;

    public static Properties getProp() {
        if (prop == null) {
            init();
        }
        return prop;
    }

    public static void setProperties(Properties prop) {
        FundamentalConfigProvider.prop = prop;
        LOGGER.info("FundamentalConfigProvider.prop ={},size:{}", prop,
                prop.size());
    }

    public Properties getProperties() {
        return getProp();
    }

    public static String get(String key) {
        return getProp().getProperty(key);
    }

    public static Integer getInt(String key) {
        String value = get(key);
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            LOGGER.error("get int error, return null.", e);
            return null;
        }
    }

    public static boolean getBoolean(String key) {
        String value = get(key);
        return Boolean.valueOf(value);
    }

    public static Double getDouble(String key) {
        String value = get(key);
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            LOGGER.error("get int error, return null.", e);
            return null;
        }
    }

    public static Long getLong(String key) {
        String value = get(key);
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            LOGGER.error("get int error, return null.", e);
            return null;
        }
    }

    private static synchronized void init() {
        if (FundamentalConfigProvider.prop != null && FundamentalConfigProvider.prop.size() > 0) {
            return;
        }
        LOGGER.info("init FundamentalConfigProvider.prop.");
        Properties properties = ConfigProperties.getProperties();
        if (properties != null && properties.size() > 0) {
            FundamentalConfigProvider.prop = properties;
            LOGGER.info("find properties from ConfigProperties");
        }
        else {
            String configPath = System.getProperty(Constants.CONFIG_PATH);
            if (configPath == null) {
                LOGGER.info("can't load config from:System.getProperty(Constants.CONFIG_PATH)");
                configPath = System.getenv(Constants.CONFIG_PATH);
                LOGGER.info("can't load config from:System.getenv(Constants.CONFIG_PATH)");
                if (configPath == null) {
                    LOGGER.error("config.path is null，now we use default config.if the environment is not dev,please check you startup param: -Dconfig.path=xxx");
                    configPath = DEFAULT_CONFIG_PATH;
                }
            }
            LOGGER.info("config.path:{}", configPath);
            Properties configs = new Properties();
            FileInputStream fileInputStream = null;
            InputStream inputStream = null;
            try {
                if (configPath.startsWith(FILE_PREFIX)) {
                    configPath = configPath.substring(FILE_PREFIX.length());
                    fileInputStream = new FileInputStream(new File(configPath));
                    configs.load(fileInputStream);
                } else if (configPath.startsWith(CLASSPATH_PREFIX)) {
                    configPath = configPath
                            .substring(CLASSPATH_PREFIX.length());
                    inputStream = FundamentalConfigProvider.class
                            .getResourceAsStream(configPath);
                    configs.load(inputStream);
                }
                FundamentalConfigProvider.prop = configs;
            } catch (FileNotFoundException e) {
                LOGGER.error("", e);
            } catch (IOException e) {
                LOGGER.error("", e);
            } finally {
                IOUtils.closeQuietly(fileInputStream);
                IOUtils.closeQuietly(inputStream);
            }
        }
        LOGGER.info("FundamentalConfigProvider.prop ={},size:{}",
                FundamentalConfigProvider.prop,
                FundamentalConfigProvider.prop.size());
    }
}
