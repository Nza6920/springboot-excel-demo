package com.niu.springboot.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 雇员类
 *
 * @author [nza]
 * @version 1.0 [2021/06/17 14:22]
 * @createTime [2021/06/17 14:22]
 */
@Data
@Accessors(chain = true)
@ApiModel("雇员类")
public class Employee {

    /**
     * 雇员名称
     */
    @ApiModelProperty("雇员名称")
    private String name;

    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    private Date birthDate;

    /**
     * 薪资
     */
    @ApiModelProperty("薪资")
    private BigDecimal payment;

    /**
     * 奖金
     */
    @ApiModelProperty("奖金")
    private BigDecimal bonus;
}
