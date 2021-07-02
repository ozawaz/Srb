package cn.distantstar.common.result;

import lombok.Data;

/**
 * @author: distantstar
 */
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    /**
     * 构造器私有
     */
    private Result(){}

    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static <T> Result<T> build(T body, ResponseEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static <T> Result<T> build(Integer code, String message) {
        Result<T> result = build(null);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static<T> Result<T> ok(){
        return Result.ok(null);
    }

    /**
     * 操作成功
     * @param data 传入的数据
     * @param <T> 数据类型
     * @return 返回成功数据
     */
    public static<T> Result<T> ok(T data){
        Result<T> result = build(data);
        return build(data, ResponseEnum.SUCCESS);
    }

    public static<T> Result<T> fail(){
        return Result.fail(null);
    }

    /**
     * 操作失败
     * @param data 传入的数据
     * @param <T> 数据类型
     * @return 返回失败的数据
     */
    public static<T> Result<T> fail(T data){
        Result<T> result = build(data);
        return build(data, ResponseEnum.ERROR);
    }

    /**
     * 设置特定的返回消息
     * @param msg 返回的消息
     * @return 返回本身
     */
    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    /**
     * 设置特定的返回状态码
     * @param code 返回的状态码
     * @return 返回本身
     */
    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        return this.getCode().intValue() == ResponseEnum.SUCCESS.getCode().intValue();
    }
}
