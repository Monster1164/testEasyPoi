package com.example.testeasypoi.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserImportVO implements Serializable, IExcelModel, IExcelDataModel {

    @NotBlank
    @Excel(name = "姓名")
    private String realName;


    @Excel(name = "出生日期", format = "yyyy-MM-dd")
    private Date birthday;

    @Email(message = "请填写正确的邮箱地址")
    @Excel(name = "邮箱")
    private String email;

    @Excel(name = "信息")
    private String errorMsg;

    private Integer rowNum;





    @Override
    public int getRowNum() {
        return this.rowNum;
    }

    @Override
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }



    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public void setErrorMsg(String s) {
        this.errorMsg = s;
    }
}