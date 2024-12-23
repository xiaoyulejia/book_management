package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

@TableName("user")
@Data
public class User {
    @TableId (type = IdType.AUTO)
    //user的id
    private Integer id;

    //姓名
    private String username;

    //昵称
    private String nickName;

    //密码
    private String password;

    //性别
    private String sex;

    //地址
    private String address;

    //电话
    private String phone;

    @TableField(exist = false)  //表中没有token不会报错仍能编译运行
    private String token;

    //角色
    private Integer role;

}
