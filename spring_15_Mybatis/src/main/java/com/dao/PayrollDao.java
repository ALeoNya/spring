package com.dao;

import com.pojo.Payroll;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface PayrollDao {
    //一位员工对应多个工资表，一对多（Mybatis里的一对一）
    @Select("select * from payroll")
    @Results(id = "payrollMap",value = {
            @Result(id = true,column = "eid",property = "eid"),
            /*
            * id = true为true表示该属性为主键
             * column为数据库中对应的的字段名
             * property对应的是数据实体类的名字
            * */
            @Result(column = "jid",property = "jid"),
            @Result(column = "salary",property = "salary"),
            @Result(column = "eid",property = "employe",one=@One(select = "com.dao.EmployeDao.findEmployeBy_eid",fetchType = FetchType.EAGER))
            /*
             * 对一时立即加载    EAGER
             * 对多时延迟加载    LAZY
             * */
    })
    List<Payroll> findAll();

    @Select("select * from payroll where eid = #{eid}")
    List<Payroll> findPayrollBy_eid(String eid);

//    @Update("UPDATE payroll SET eid = 'un' where eid = #{eid}")
//    List<Payroll> updatePayrollBy_eid(String eid);

    @Delete("DELETE payroll,profession FROM payroll LEFT JOIN profession ON payroll.jid = profession.jid WHERE payroll.jid = #{jid}")
    void deletePayrollBy_jid(String jid);



//    USE company;
//    DELETE payroll,profession FROM payroll
//    LEFT JOIN profession ON payroll.jid = profession.jid
//    WHERE payroll.jid = 'j04'
}
