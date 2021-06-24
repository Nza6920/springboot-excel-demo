package com.niu.poi.service;

import com.niu.poi.domain.dto.ExcelTemplateDto;
import com.niu.poi.domain.dto.GradeInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Excel 业务类
 *
 * @author [nza]
 * @version 1.0 [2021/06/07 13:56]
 * @createTime [2021/06/07 13:56]
 */
public interface ExcelService {

    /**
     * 导出 Workbook
     *
     * @param dto 模板参数
     * @return {@link Workbook}
     * @author nza
     */
    Workbook getTemplate(ExcelTemplateDto dto);

    /**
     * 获取成绩列表
     *
     * @param file Excel文件
     * @return {@link java.util.List<com.niu.poi.domain.dto.GradeInfo>}
     * @author nza
     * @createTime 2021/6/7 14:02
     */
    List<GradeInfo> getGradeInfoList(File file) throws IOException;

    /**
     * 执行 {@link com.niu.poi.controller.ExcelController::getGradeInfoList} 业务逻辑
     *
     * @param file Excel 文件
     * @return {@link java.util.List<com.niu.poi.domain.dto.GradeInfo>}
     * @author nza
     * @createTime 2021/6/7 14:30
     */
    List<GradeInfo> doGetGradeInfoList(MultipartFile file) throws IOException;
}
