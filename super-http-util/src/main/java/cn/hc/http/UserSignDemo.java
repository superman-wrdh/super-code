package cn.hc.http;

import redis.clients.jedis.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 基于Redis位图的用户签到功能实现类
 * <p>
 * 实现功能：
 * 1. 用户签到
 * 2. 检查用户是否签到
 * 3. 获取当月签到次数
 * 4. 获取当月连续签到次数
 * 5. 获取当月首次签到日期
 * 6. 获取当月签到情况
 */
public class UserSignDemo {
    private static ShardedJedis jedis;
    private static ShardedJedisPool pool;

    static {
        // 配置Redis信息
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(50);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        // 集群
        JedisShardInfo jedisShardInfo1 = new JedisShardInfo("192.168.199.199", 6379);

        // 设置Redis的密码
        jedisShardInfo1.setPassword("hcissupermanredis");

        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
        list.add(jedisShardInfo1);
        pool = new ShardedJedisPool(config, list);
        jedis = pool.getResource();
    }

    /**
     * 用户签到
     *
     * @param uid  用户ID
     * @param date 日期
     * @return 之前的签到状态
     */
    public boolean doSign(int uid, LocalDate date) {
        int offset = date.getDayOfMonth() - 1;
        return jedis.setbit(buildSignKey(uid, date), offset, true);
    }

    /**
     * 检查用户是否签到
     *
     * @param uid  用户ID
     * @param date 日期
     * @return 当前的签到状态
     */
    public boolean checkSign(int uid, LocalDate date) {
        int offset = date.getDayOfMonth() - 1;
        return jedis.getbit(buildSignKey(uid, date), offset);
    }

    /**
     * 获取用户签到次数
     *
     * @param uid  用户ID
     * @param date 日期
     * @return 当前的签到次数
     */
    public long getSignCount(int uid, LocalDate date) {
        return jedis.bitcount(buildSignKey(uid, date));
    }

    /**
     * 获取当月连续签到次数
     *
     * @param uid  用户ID
     * @param date 日期
     * @return 当月连续签到次数
     */
    public long getContinuousSignCount(int uid, LocalDate date) {
        int signCount = 0;
        String type = String.format("u%d", date.getDayOfMonth());
        List<Long> list = jedis.bitfield(buildSignKey(uid, date), "GET", type, "0");
        if (list != null && list.size() > 0) {
            // 取低位连续不为0的个数即为连续签到次数，需考虑当天尚未签到的情况
            long v = list.get(0) == null ? 0 : list.get(0);
            for (int i = 0; i < date.getDayOfMonth(); i++) {
                if (v >> 1 << 1 == v) {
                    // 低位为0且非当天说明连续签到中断了
                    if (i > 0) break;
                } else {
                    signCount += 1;
                }
                v >>= 1;
            }
        }
        return signCount;
    }

    /**
     * 获取当月首次签到日期
     *
     * @param uid  用户ID
     * @param date 日期
     * @return 首次签到日期
     */
    public LocalDate getFirstSignDate(int uid, LocalDate date) {
        long pos = jedis.bitpos(buildSignKey(uid, date), true);
        return pos < 0 ? null : date.withDayOfMonth((int) (pos + 1));
    }

    /**
     * 获取当月签到情况
     *
     * @param uid  用户ID
     * @param date 日期
     * @return Key为签到日期，Value为签到状态的Map
     */
    public Map<String, Boolean> getSignInfo(int uid, LocalDate date) {
        Map<String, Boolean> signMap = new HashMap<>(date.getDayOfMonth());
        String type = String.format("u%d", date.lengthOfMonth());
        List<Long> list = jedis.bitfield(buildSignKey(uid, date), "GET", type, "0");
        if (list != null && list.size() > 0) {
            // 由低位到高位，为0表示未签，为1表示已签
            long v = list.get(0) == null ? 0 : list.get(0);
            for (int i = date.lengthOfMonth(); i > 0; i--) {
                LocalDate d = date.withDayOfMonth(i);
                signMap.put(formatDate(d, "yyyy-MM-dd"), v >> 1 << 1 != v);
                v >>= 1;
            }
        }
        return signMap;
    }

    private static String formatDate(LocalDate date) {
        return formatDate(date, "yyyyMM");
    }

    private static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    private static String buildSignKey(int uid, LocalDate date) {
        return String.format("u:sign:%d:%s", uid, formatDate(date));
    }

    public static void main(String[] args) {
        UserSignDemo demo = new UserSignDemo();
        LocalDate today = LocalDate.now();
        LocalDate yestaday = LocalDate.parse("2019-10-28");

        { // doSign
            boolean signed = demo.doSign(1000, today);
            demo.doSign(1000,yestaday);
            if (signed) {
                System.out.println("您已签到：" + formatDate(today, "yyyy-MM-dd"));
            } else {
                System.out.println("签到完成：" + formatDate(today, "yyyy-MM-dd"));
            }
        }

        { // checkSign
            boolean signed = demo.checkSign(1000, today);
            if (signed) {
                System.out.println("您已签到：" + formatDate(today, "yyyy-MM-dd"));
            } else {
                System.out.println("尚未签到：" + formatDate(today, "yyyy-MM-dd"));
            }
        }

        { // getSignCount
            long count = demo.getSignCount(1000, today);
            System.out.println("本月签到次数：" + count);
        }

        { // getContinuousSignCount
            long count = demo.getContinuousSignCount(1000, today);
            System.out.println("连续签到次数：" + count);
        }

        { // getFirstSignDate
            LocalDate date = demo.getFirstSignDate(1000, today);
            System.out.println("本月首次签到：" + formatDate(date, "yyyy-MM-dd"));
        }

        { // getSignInfo
            System.out.println("当月签到情况：");
            Map<String, Boolean> signInfo = new TreeMap<>(demo.getSignInfo(1000, today));
            for (Map.Entry<String, Boolean> entry : signInfo.entrySet()) {
                System.out.println(entry.getKey() + ": " + (entry.getValue() ? "√" : "-"));
            }
        }
    }

}
