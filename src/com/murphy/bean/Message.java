package com.murphy.bean;

/**
 * @author murphy
 */
public class Message {
    // {status:0,result:"",data:{}}
    /**
     * 状态码
     *  0 - 成功
     * -1 - 失败
     */
    private int status;
    /**
     * 消息内容
     */
    private String result;
    /**
     * 消息所携带的一组数据
     */
    private Object data;

    public Message() {
    }

    public Message(int status, String result) {
        this.status = status;
        this.result = result;
    }

    public Message(Object data) {
        this.data = data;
    }

    public Message(String result) {
        this.result = result;
    }

    public Message(int status) {
        this.status = status;
    }

    public Message(int status, String result, Object data) {
        this.status = status;
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
