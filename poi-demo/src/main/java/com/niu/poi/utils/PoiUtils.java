package com.niu.poi.utils;

/**
 * Poi 工具类
 *
 * @author [nza]
 * @version 1.0 [2021/06/04 10:55]
 * @createTime [2021/06/04 10:55]
 */
public class PoiUtils {

    /**
     * 转换宽度值
     *
     * @param width 宽
     * @return int
     * @author nza
     * @createTime 2021/6/4 10:56
     */
    public static int convert2PoiWidth(int width) {
        if (width < 0) {
            throw new IllegalArgumentException("非法的参数");
        }
        return 256 * width + 184;
    }
}
