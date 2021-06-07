package com.niu.poi.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel("Excel 模板信息")
public class ExcelTemplateDto {

    /**
     * 标题
     */
    @NotEmpty(message = "标题不能为空")
    @Size(max = 16, message = "标题格式错误")
    @ApiModelProperty("标题")
    private String title;

    /**
     * 列
     */
    @NotNull(message = "列数量不能为空")
    @Size(min = 1, max = 100, message = "列数量非法")
    @ApiModelProperty("列集合")
    private List<String> columns;
}
