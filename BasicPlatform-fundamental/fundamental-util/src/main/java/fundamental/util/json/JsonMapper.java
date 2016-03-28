package fundamental.util.json;

import fundamental.logger.FundamentalLogger;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.util.JSONPObject;
import org.codehaus.jackson.type.JavaType;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: YangRichard
 * Date: 14-10-26
 * Time: 下午9:40
 * To change this template use File | Settings | File Templates.
 */
public class JsonMapper {
    private static FundamentalLogger logger = FundamentalLogger.getLogger(JsonMapper.class);
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ObjectMapper mapper;
    public JsonMapper(JsonSerialize.Inclusion inclusion) {
        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        mapper.setSerializationInclusion(inclusion);
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.configure(
                DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 禁止使用int代表Enum的order()來反序列化Enum,非常危險
        mapper.configure(
                DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
                false);
        mapper.setDateFormat(this.dateFormat);
        mapper.setSerializationConfig(mapper.getSerializationConfig()
                .withDateFormat(this.dateFormat));
        mapper.setDeserializationConfig(mapper.getDeserializationConfig()
                .withDateFormat(this.dateFormat));
    }

    /**
     * 创建输出全部属性到Json字符串的Mapper.
     */
    public static JsonMapper buildNormalMapper() {
        return new JsonMapper(JsonSerialize.Inclusion.ALWAYS);
    }

    /**
     * 创建只输出非空属性到Json字符串的Mapper.
     */
    public static JsonMapper buildNonNullMapper() {
        return new JsonMapper(JsonSerialize.Inclusion.NON_NULL);
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper.
     */
    public static JsonMapper buildNonDefaultMapper() {
        return new JsonMapper(JsonSerialize.Inclusion.NON_DEFAULT);
    }

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper.
     */
    public static JsonMapper buildNonEmptyMapper() {
        return new JsonMapper(JsonSerialize.Inclusion.NON_EMPTY);
    }

    /**
     * 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object) {

        try {
            String result = mapper.writeValueAsString(object);
            result = result.replaceAll("<", "&lt;");
            result = result.replaceAll(">", "&gt;");
            return result;
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     *
     * 如需读取集合如List/Map, 且不是List<String>这种简单类型时,先使用函數constructParametricType构造类型.
     *
     * @see #constructParametricType(Class, Class...)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     *
     * 如需读取集合如List/Map, 且不是List<String>这种简单类型时,先使用函數constructParametricType构造类型.
     *
     * @see #constructParametricType(Class, Class...)
     */
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 構造泛型的Type如List<MyBean>,
     * 则调用constructParametricType(ArrayList.class,MyBean.class)
     * Map<String,MyBean>则调用(HashMap.class,String.class, MyBean.class)
     */
    public JavaType constructParametricType(Class<?> parametrized,
                                            Class<?>... parameterClasses) {
        return mapper.getTypeFactory().constructParametricType(parametrized,
                parameterClasses);
    }

    /**
     * 當JSON裡只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
     */
    public <T> T update(T object, String jsonString) {
        try {
            return (T) mapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            logger.warn("update json string:" + jsonString + " to object:"
                    + object + " error.", e);
        } catch (IOException e) {
            logger.warn("update json string:" + jsonString + " to object:"
                    + object + " error.", e);
        }
        return null;
    }

    /**
     * 輸出JSONP格式數據.
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 設定是否使用Enum的toString函數來讀寫Enum, 為False時時使用Enum的name()函數來讀寫Enum, 默認為False.
     * 注意本函數一定要在Mapper創建後, 所有的讀寫動作之前調用.
     */
    public void setEnumUseToString(boolean value) {
        mapper.configure(
                SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING, value);
        mapper.configure(
                DeserializationConfig.Feature.READ_ENUMS_USING_TO_STRING, value);
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return mapper;
    }
}
