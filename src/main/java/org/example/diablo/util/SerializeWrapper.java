package org.example.diablo.util;


/**
 *
 * collection类型和map类型的序列化包装类
 *
 * @author vincent
 * @version 1.0
 * @since 2019-04-29 11:22:17
 */
public class SerializeWrapper<T> {

    private T data;

    public static <T> SerializeWrapper<T> builder(T data) {
        SerializeWrapper<T> wrapper = new SerializeWrapper<>();
        wrapper.setData(data);
        return wrapper;
    }

    public T getData() {
        return data;
    }

    private void setData(T data) {
        this.data = data;
    }
}
