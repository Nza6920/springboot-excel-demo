package com.niu.poi.controller;

import com.google.common.collect.Maps;
import com.niu.poi.domain.dto.ExcelTemplateDto;
import com.niu.poi.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * <功能简述>
 *
 * @author [nza]
 * @version 1.0 [2021/06/03 16:01]
 * @createTime [2021/06/03 16:01]
 */
@RestController
@RequestMapping("/excels")
@Slf4j
@Api("Excel 控制器")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    /**
     * 下载 Excel 模板
     *
     * @param dto      {@link ExcelTemplateDto dto}
     * @param response {@link HttpServletResponse}
     * @author nza
     * @createTime 2021/6/7 14:08
     */
    @PostMapping("/template")
    @ApiOperation("下载 Excel 模板")
    public void download(@Valid @RequestBody @ApiParam("模板信息") ExcelTemplateDto dto, HttpServletResponse response) {
        Workbook workbook = excelService.getTemplate(dto);
        String fileName = "res" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + ".xlsx";

        try (OutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
            workbook.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            log.error("导出 Excel 失败");
        }
    }

    /**
     * 获取分数列表
     *
     * @param file 文件
     * @return {@link java.util.Map<java.lang.String,java.lang.Object>}
     * @author nza
     * @createTime 2021/6/7 14:15
     */
    @PostMapping("/grades")
    @ApiOperation("获取分数列表")
    public Map<String, Object> getGradeInfoList(@RequestParam("file") @ApiParam("模板文件") MultipartFile file) throws IOException {
        Map<String, Object> res = Maps.newHashMap();
        res.put("data", excelService.doGetGradeInfoList(file));
        return res;
    }
}
