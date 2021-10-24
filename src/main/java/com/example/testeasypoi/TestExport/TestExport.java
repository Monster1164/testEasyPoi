package com.example.testeasypoi.TestExport;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.example.testeasypoi.pojo.UserImportVO;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class TestExport {

    @Test
    public void export() throws IOException {
        ArrayList<UserImportVO> objects = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            UserImportVO userImportVO = new UserImportVO();
            userImportVO.setBirthday(new Date());
            userImportVO.setRealName("张"+i);
            userImportVO.setEmail(i+"qq.com");
            objects.add(userImportVO);
        }

        File savefile = new File("E:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }

        ExportParams exportParams = new ExportParams("test", "test", ExcelType.XSSF);

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), UserImportVO.class, objects);

        FileOutputStream fos = new FileOutputStream("E:/excel/test.xls");
        workbook.write(fos);
        fos.close();

    }
}
