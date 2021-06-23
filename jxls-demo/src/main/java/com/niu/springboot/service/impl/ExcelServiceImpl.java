package com.niu.springboot.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import com.google.common.collect.Lists;
import com.niu.springboot.pojo.Employee;
import com.niu.springboot.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
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
    @Override
    public File getEmployeesExcel() throws IOException {
        log.info("Running Object Collection demo");
        File outFile = new File("D:/test/object_collection_output.xls");
        List<Employee> employees = Lists.newArrayList();
        for (int i = 10; i > 0; i--) {
            Employee employee = new Employee()
                    .setName("雇员" + i)
                    .setBirthDate(new Date())
                    .setPayment(BigDecimal.valueOf(i * 5000L))
                    .setBonus(BigDecimal.valueOf(i * 1000L));
            employees.add(employee);
        }

        try (InputStream is = ResourceUtil.getStream("static/employee_template.xls")) {
            try (OutputStream os = new FileOutputStream(outFile)) {
                Context context = new Context();
                context.putVar("employees", employees);
//                JxlsHelper.getInstance().processTemplate(is, os, context);
                JxlsHelper.getInstance().processTemplateAtCell(is, os, context, "Result!A1");
                os.flush();
            }
        }
        return outFile;
    }
}
