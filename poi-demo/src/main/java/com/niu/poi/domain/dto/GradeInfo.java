package com.niu.poi.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 成绩信息
 *
 * @author [nza]
 * @version 1.0 [2021/06/07 13:58]
 * @createTime [2021/06/07 13:58]
 */
@Data
@Accessors(chain = true)
public class GradeInfo {

    /**
     * 学生姓名
     */
    private String stuName;

    /**
     * 科目
     */
    private String subject;

    /**
     * 分数
     */
    private Double score;
}
