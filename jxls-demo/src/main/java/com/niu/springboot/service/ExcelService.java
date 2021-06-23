package com.niu.springboot.service;

import java.io.File;
import java.io.IOException;

/**
 * Excel 业务类
 *
 * @author [nza]
 * @version 1.0 [2021/06/22 10:31]
 * @createTime [2021/06/22 10:31]
 */
public interface ExcelService {

    /**
     * 获取雇员 Excel 列表
     *
     * @return {@link java.io.File}
     * @exception  {@link IOException}
     * @author nza
     * @createTime 2021/6/22 10:36
     */
    File getEmployeesExcel() throws IOException;
}
