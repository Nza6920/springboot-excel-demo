package com.niu.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.niu.springboot.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Excel 控制器
 *
 * @author [nza]
 * @version 1.0 [2021/06/22 11:10]
 * @createTime [2021/06/22 11:10]
 */
@RestController
@RequestMapping("/excel")
@Slf4j
@Api("excel 控制器")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/employees")
    @ApiOperation("下载 Excel 模板")
    public void download(HttpServletResponse response) {
        String fileName = "res" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + ".xls";
        try (OutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
            File employeesExcel = excelService.getEmployeesExcel();
            IoUtil.write(outputStream, false, FileUtil.readBytes(employeesExcel));
            outputStream.flush();
        } catch (Exception e) {
            log.error("导出 Excel 失败: ", e);
        }
    }
}
