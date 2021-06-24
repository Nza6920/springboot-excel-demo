package com.niu.jxls.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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
    @NotEmpty(message = "雇员名称不能为空")
    private String name;

    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "出生日期不能为空")
    @Past(message = "日期格式非法")
    private Date birthDate;

    /**
     * 薪资
     */
    @ApiModelProperty("薪资")
    @NotNull(message = "薪资不能为空")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal payment;

    /**
     * 奖金
     */
    @ApiModelProperty("奖金")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal bonus;
}
