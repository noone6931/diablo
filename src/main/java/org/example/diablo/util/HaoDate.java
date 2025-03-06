package org.example.diablo.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 私有日期类型
 *
 * @author GaoJian
 * @version 1.0
 * @since 2017-09-07 17:03:13
 */
public class HaoDate extends Date {

    private static final long serialVersionUID = 7528567971034268905L;

    /**
     * 是否为全零时间
     */
    private boolean zero = false;

    /**
     * 全零时间的字符串形式
     */
    private static final String zeroTime = "0000-00-00 00:00:00";

    /**
     * 全零日期的字符串形式
     */
    private static final String zeroDate = "0000-00-00";

    /**
     * “年年年年-月月-日日”的时间格式
     */
    private static final String datePattern = "yyyy-MM-dd";

    /**
     * 线程安全的日期格式化器
     */
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

    /**
     * “年年年年-月月-日日 时时:分分:秒秒”的时间格式
     */
    private static final String timestampPattern = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(timestampPattern);

    /**
     * 全零日期的HaoDate实例
     */
    public static final HaoDate ZERO_INST = new HaoDate(true);

    /**
     * jdk的LocalDateTime
     */
    private LocalDateTime localDateTime;

    /**
     * jdk的LocalDate
     */
    private LocalDate localDate;

    /**
     * 私有构造器，专门用于创建全零实例
     *
     * @param zero 是否全零实例，此处只会传true，为了区别其它构造器而设置了这个参数
     */
    private HaoDate(boolean zero) {
        this.zero = zero;
    }

    /**
     * 私有构造器，根据LocalDate创建实例
     *
     * @param localDate jdk的LocalDate
     */
    private HaoDate(LocalDate localDate) {
        this(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
    }

    /**
     * 私有构造器，根据LocalDateTime创建实例
     *
     * @param localDateTime jdk的LocalDateTime
     */
    private HaoDate(LocalDateTime localDateTime) {
        this(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()).getTime());
    }

    /**
     * 无参构造器
     */
    public HaoDate() {
        super();
        this.localDateTime = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.localDate = localDateTime.toLocalDate();
    }



    /**
     * 构造器
     *
     * @param date 长整型时间戳
     */
    public HaoDate(long date) {
        super(date);
        this.localDateTime = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.localDate = localDateTime.toLocalDate();
        if (0 == date) {
            this.zero = true;
        }
    }


    @Override
    public long getTime() {
        if (isZeroTime()) {
            return 0;
        }
        return super.getTime();
    }


    /**
     * 判断是否为全零时间
     *
     * @return 是否全零时间
     */
    public boolean isZeroTime() {
        return zero;
    }

    /**
     * 格式化成给定的格式
     *
     * @param pattern 预期的格式
     * @return 预期格式的字符串
     */
    public String format(String pattern) {
        if (this.isZeroTime()) {
            return zeroTime;
        }
        return new SimpleDateFormat(pattern).format(this);
    }




    @Override
    public String toString() {
        if (isZeroTime()) {
            return zeroTime;
        }
        return super.toString();
    }
}
