package com.niu.jxls.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import com.google.common.collect.Lists;
import com.niu.jxls.domain.dto.EmployeeDto;
import com.niu.jxls.pojo.Employee;
import com.niu.jxls.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/**
 * Excel 业务实现类
 *
 * @author [nza]
 * @version 1.0 [2021/06/22 10:38]
 * @createTime [2021/06/22 10:38]
 */
@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    static final String TEMPLATE_DIR = "D:/test/object_collection_output.xlsx";

    @Override
    public File getEmployeesExcel(EmployeeDto dto) throws IOException {
        log.info("Running Object Collection demo");
        File outFile = new File(TEMPLATE_DIR);
        List<Employee> employees = Lists.newArrayList(dto.getEmployees());

        try (InputStream is = ResourceUtil.getStream("static/employee_template.xlsx")) {
            try (OutputStream os = new FileOutputStream(outFile)) {
                Context context = new Context();
                context.putVar("employees", employees);
                context.putVar("title", dto.getTitle());
                JxlsHelper.getInstance().processTemplate(is, os, context);
                // 指定 sheet
                // JxlsHelper.getInstance().processTemplateAtCell(is, os, context, "Result!A1");
                os.flush();
            }
        }
        return outFile;
    }
}
