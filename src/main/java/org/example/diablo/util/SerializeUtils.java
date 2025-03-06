package org.example.diablo.util;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * Java对象序列化、反序列化工具类
 *
 * @author vincent
 * @version 1.0
 * @since 2019-04-08 16:01:11
 */
public class SerializeUtils {
    private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);

    private static final Map<String, Schema<?>> schemaMap = new ConcurrentHashMap<>();

    private static final Schema<SerializeWrapper> wrapperSchema = RuntimeSchema.createFrom(SerializeWrapper.class);


    private static <T> Schema<T> getSchema(Class<T> clazz) {
        return RuntimeSchema.createFrom(clazz);
//        if (!schemaMap.containsKey(clazz.getName())) {
//            Schema<T> schema = RuntimeSchema.getSchema(clazz);
//            if (Objects.nonNull(schema)) {
//                schemaMap.put(clazz.getName(), schema);
//            }
//        }
//        return (Schema<T>) schemaMap.get(clazz);
    }

    /**
     * Java对象序列化方法
     * @param object 需要序列化的对象
     * @param <T> 泛型
     * @return 序列化后的结果
     */
    public static <T> byte[] serialize(T object) {
        if (null == object) {
            return null;
        }
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Class<T> clazz = (Class<T>) object.getClass();
            Schema schema = wrapperSchema;
            Object serializeObject = object;
            if (Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz)) {
                serializeObject = SerializeWrapper.builder(object);
            } else {
                schema = getSchema(clazz);
            }
            return ProtostuffIOUtil.toByteArray(serializeObject, schema, buffer);
        } catch (Exception e) {
            logger.error("serialize [{}] exception: {}", object, e);
            throw new IllegalStateException(e);
        } finally {
            buffer.clear();
        }
    }

    /**
     * Java对象反序列化方法
     * @param data 需要反序列化的数据
     * @param clazz 反序列化后的类型
     * @param <T> 泛型
     * @return 反序列化后的结果
     */
    public static <T> T deserialize(byte[] data, Class<T> clazz) {
        if (null == data) {
            return null;
        }
        try {
            if (Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz)) {
                SerializeWrapper<T> wrapper = new SerializeWrapper<>();
                ProtostuffIOUtil.mergeFrom(data, wrapper, wrapperSchema);
                return wrapper.getData();
            } else {
                Schema<T> schema = getSchema(clazz);
                T object = schema.newMessage();
                ProtostuffIOUtil.mergeFrom(data, object, schema);
                return object;
            }
        } catch (Exception e) {
            logger.error("deserialize [{}] exception: {}", clazz.getName(), e);
            throw new IllegalStateException(e);
        }
    }
}
