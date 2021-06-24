package com.niu.jxls.domain.dto;

import com.niu.jxls.pojo.Employee;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Excel模板
 *
 * @author [nza]
 * @version 1.0 [2021/06/04 11:18]
 * @createTime [2021/06/04 11:18]
 */
@Data
@ApiModel("员工信息")
public class EmployeeDto {

    /**
     * 标题
     */
    @NotEmpty(message = "标题不能为空")
    @Size(max = 16, message = "标题格式错误")
    @ApiModelProperty("标题")
    private String title;

    /**
     * 员工集合
     */
    @NotNull(message = "员工不能为空")
    @Size(min = 1, max = 1000, message = "员工数量非法")
    @ApiModelProperty("员工集合")
    @Valid
    private List<Employee> employees;
}
