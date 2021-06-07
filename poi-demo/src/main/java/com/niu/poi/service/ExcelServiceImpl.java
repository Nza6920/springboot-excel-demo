package com.niu.poi.service;

import cn.hutool.core.util.StrUtil;
import com.niu.poi.constant.enums.GradeColumnEnum;
import com.niu.poi.domain.dto.ExcelTemplateDto;
import com.niu.poi.domain.dto.GradeInfo;
import com.niu.poi.utils.PoiUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Excel 业务类
 *
 * @author [nza]
 * @version 1.0 [2021/06/03 16:02]
 * @createTime [2021/06/03 16:02]
 */
@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    @Value("${os.file.temp-dir}")
    private String tempDir;

    static final short CUSTOM_GREEN = 9;

    static final short DEFAULT_ROW_HEIGHT;

    static final int DEFAULT_COLUMN_WIDTH;

    static {
        DEFAULT_ROW_HEIGHT = (short) (43 * 20);
        DEFAULT_COLUMN_WIDTH = PoiUtils.convert2PoiWidth(37);
    }

    @Override
    public Workbook getTemplate(ExcelTemplateDto dto) {

        log.info("开始创建工作簿...");

        // 创建工作簿
        // 生成.xlsx的excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 自定义颜色
        customColors(workbook);
        // 创建工作表
        Sheet sheet = workbook.createSheet();
        sheet.setDefaultRowHeight(DEFAULT_ROW_HEIGHT);
        sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);

        // 第一行
        buildRow1(workbook, sheet, dto);
        // 第二行
        buildRow2(workbook, sheet, dto);

        return workbook;
    }

    @Override
    public List<GradeInfo> getGradeInfoList(File file) throws IOException {
        InputStream ips = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(ips);
        HSSFSheet sheet = wb.getSheetAt(0);

        List<GradeInfo> gradeInfoList = Lists.newArrayList();
        for (Row row : sheet) {
            // 跳过表头和空行
            if (row.getRowNum() <= 1 || row.getFirstCellNum() < 0) {
                continue;
            }
            if (!isValid(row.getCell(row.getFirstCellNum()))) {
                break;
            }
            GradeInfo gradeInfo = new GradeInfo();
            for (Cell cell : row) {
                GradeColumnEnum columnIndex = GradeColumnEnum.SCORE_COLUMN_INDEX.getEnumByNum(cell.getColumnIndex());
                switch (columnIndex) {
                    case STU_NAME_COLUMN_INDEX:
                        String stuName = cell.getStringCellValue();
                        gradeInfo.setStuName(stuName);
                        break;
                    case SUBJECT_COLUMN_INDEX:
                        String subject = cell.getStringCellValue();
                        gradeInfo.setSubject(subject);
                        break;
                    default:
                        double score = cell.getNumericCellValue();
                        gradeInfo.setScore(score);
                }
            }
            gradeInfoList.add(gradeInfo);
        }

        return gradeInfoList;
    }

    /**
     * 校验值是否合法
     *
     * @param cell {@link Cell}
     * @return {@link Boolean}
     */
    private boolean isValid(Cell cell) {
        switch (cell.getCellType()) {
            case NUMERIC:
                double numericCellValue = cell.getNumericCellValue();
                if (numericCellValue == 0) {
                    return false;
                }
                break;
            case STRING:
                String stringCellValue = cell.getStringCellValue();
                if (StrUtil.isEmpty(stringCellValue)) {
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public List<GradeInfo> doGetGradeInfoList(MultipartFile file) throws IOException {
        String fileName = tempDir + "/" + System.currentTimeMillis() + "-" + file.getOriginalFilename();
        File tempFile = new File(fileName);
        try {
            file.transferTo(tempFile);
            return getGradeInfoList(tempFile);
        } catch (IOException e) {
            log.error("读取 Excel 文件失败：", e);
            throw e;
        } finally {
            if (tempFile.exists()) {
                if (!tempFile.delete()) {
                    log.error("删除文件失败，文件地址：{}", tempFile.getPath());
                }
            }
        }
    }

    /**
     * 构建第一行
     *
     * @param workbook 工作簿
     * @param sheet    sheet
     * @param dto      模板信息
     * @author nza
     * @createTime 2021/6/4 11:00
     */
    private void buildRow1(HSSFWorkbook workbook, Sheet sheet, ExcelTemplateDto dto) {
        CellStyle cellStyle1 = buildHead1CellStyle(workbook);
        Row row1 = sheet.createRow(0);
        row1.setHeight(DEFAULT_ROW_HEIGHT);
        Cell cell = row1.createCell(0);
        cell.setCellValue(dto.getTitle());
        cell.setCellStyle(cellStyle1);

        // 合并单元格
        if (dto.getColumns().size() > 1) {
            int lastColumn = cell.getColumnIndex() + dto.getColumns().size() - 1;
            CellRangeAddress region = new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), cell.getColumnIndex(), lastColumn);
            sheet.addMergedRegion(region);
        }
    }

    /**
     * 构建第二行
     *
     * @param workbook 工作簿
     * @param sheet    sheet
     * @param dto      模板信息
     * @author nza
     * @createTime 2021/6/4 10:59
     */
    private void buildRow2(HSSFWorkbook workbook, Sheet sheet, ExcelTemplateDto dto) {

        // 列
        List<String> columns = dto.getColumns();

        CellStyle cellStyle2 = buildHead2CellStyle(workbook);
        Row row2 = sheet.createRow(1);

        for (int i = 0; i < columns.size(); i++) {

            Cell cell = row2.createCell(i);
            cell.setCellValue(columns.get(i));
            cell.setCellStyle(cellStyle2);
            // 设置列宽
            sheet.setColumnWidth(cell.getColumnIndex(), PoiUtils.convert2PoiWidth(37));
        }

        // 设置行高
        row2.setHeight(DEFAULT_ROW_HEIGHT);
    }

    /**
     * 设置头部第一行表格样式
     *
     * @author nza
     */
    private CellStyle buildHead1CellStyle(HSSFWorkbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        // 设置字体大小
        font.setFontHeightInPoints((short) 24);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);

        // 设置填充模式
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorderColor(cellStyle);

        return cellStyle;
    }

    /**
     * 设置边框颜色
     *
     * @param cellStyle {@link CellStyle}
     * @author nza
     * @createTime 2021/6/7 10:48
     */
    private void setBorderColor(CellStyle cellStyle) {
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
    }

    /**
     * 设置头部第二行表格样式
     *
     * @author nza
     */
    private CellStyle buildHead2CellStyle(HSSFWorkbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        // 设置字体大小
        font.setFontHeightInPoints((short) 16);


        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(CUSTOM_GREEN);

        // 设置填充模式
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorderColor(cellStyle);

        return cellStyle;
    }

    /**
     * 自定义颜色
     *
     * @param workbook 工作簿
     * @author nza
     * @createTime 2021/6/4 11:04
     */
    private void customColors(HSSFWorkbook workbook) {
        // 自定义颜色
        HSSFPalette customPalette = workbook.getCustomPalette();
        customPalette.setColorAtIndex(CUSTOM_GREEN, (byte) (0xff & 130), (byte) (0xff & 176), (byte) (0xff & 155));
    }
}
