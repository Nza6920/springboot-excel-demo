package com.niu.poi.constant.enums;

/**
 * Excel 列枚举
 *
 * @author [nza]
 * @version 1.0 [2021/06/07 15:53]
 * @createTime [2021/06/07 15:53]
 */
public enum GradeColumnEnum {

    /**
     * 枚举
     */
    STU_NAME_COLUMN_INDEX(0),
    SUBJECT_COLUMN_INDEX(1),
    SCORE_COLUMN_INDEX(2);

    GradeColumnEnum(int num) {
        this.num = num;
    }

    /**
     * 列序号
     */
    private final int num;

    public int getNum() {
        return num;
    }

    public GradeColumnEnum getEnumByNum(int num) {
        for (GradeColumnEnum value : GradeColumnEnum.values()) {
            if (value.getNum() == num) {
                return value;
            }
        }
        throw new IllegalArgumentException("非法的枚举值");
    }
}
