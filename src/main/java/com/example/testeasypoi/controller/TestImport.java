package com.example.testeasypoi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.example.testeasypoi.pojo.UserImportVO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestImport {


    @PostMapping("/excel")
    public String export(MultipartFile file, HttpServletResponse response) throws Exception {

        //一.使用EasyPoi获取文件数据
        ImportParams params = new ImportParams();
        params.setHeadRows(1);

        ExcelImportResult<UserImportVO> result = ExcelImportUtil.importExcelMore(
                file.getInputStream(),
                UserImportVO.class, params);
        // 正确的数据
        List<UserImportVO> list = result.getList();
        System.out.println("打印错误信息");
        list.forEach(l1-> System.out.println(l1));
        //错误的数据
        List<UserImportVO> failList = result.getFailList();

        //制造错误数据
        ArrayList<UserImportVO> objects = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserImportVO userImportVO = new UserImportVO();
            userImportVO.setBirthday(new Date());
            userImportVO.setRealName("张"+i);
            userImportVO.setEmail(i+"qq.com");
            userImportVO.setErrorMsg(i+"错误了");
            objects.add(userImportVO);
        }

        result.setFailList(objects);
        result.setVerfiyFail(true);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), UserImportVO.class, objects);
        result.setFailWorkbook(workbook);
        //四.如果有错误，把错误数据返回到前台(让前台下载一个错误的excel)
        //4.1判断是否有错误
        if(result.isVerfiyFail()){
            //4.2拿到错误的文件薄
            Workbook failWorkbook = result.getFailWorkbook();

            //把这个文件导出
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); //mime类型
            response.setHeader("Content-disposition", "attachment;filename=error.xlsx"); //告诉浏览下载的是一个附件，名字叫做error.xlsx
            response.setHeader("Pragma", "No-cache");//设置不要缓存
            OutputStream ouputStream = response.getOutputStream();
            failWorkbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        }

        return "import";

    }
}
