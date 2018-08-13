package com.pinyougou.entity;

import java.io.Serializable;

/**
 * 封装结果类
 * @author 三国的包子
 * @version 1.0
 * @description 描述
 * @title 标题
 * @date 2018/6/26
 * @package com.pinyougou
 * @company www.itheima.com
 */

public class ResultModel  implements Serializable{
    private boolean success;//是否成功
    private String message;//信息
    private Object data ; //数据

    /**
     * 返回OK 没有信息，没有数据
     * @return
     */
    public static ResultModel ok(){
        return new ResultModel(true,null,null);
    }

    /**
     * 返回操作错误,没有数据
     * @return
     */
    public static ResultModel error(){
        return new ResultModel(false,"操作失败",null);
    }

    /**
     * 返回OK带数据
     * @param data
     * @return
     */
    public static ResultModel ok(Object data){
        return new ResultModel(true,null,data);
    }

    public ResultModel(){

    }

    public ResultModel(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
