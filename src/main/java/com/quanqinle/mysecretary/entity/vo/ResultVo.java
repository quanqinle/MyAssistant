package com.quanqinle.mysecretary.entity.vo;

/**
 * 自定义响应结构
 * @author quanqinle
 */
public class ResultVo<T> {

	/**
	 * 响应业务状态
	 * 200：成功
	 * 400：客户端输入参数有误导致响应失败
	 * 500：服务器错误
	 */
	private Integer code;

	/**
	 * 响应消息
	 */
	private String msg;

	/**
	 * 响应中的数据
	 */
	private T data;

	public ResultVo() {
	}

	public ResultVo(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResultVo(Integer code, T data) {
		this.code = code;
		this.data = data;
	}

	public ResultVo(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static <T> ResultVo<T> success(Class<T> classType) {
		return new ResultVo<T>(200, "");
	}

	public static <T> ResultVo<T> success(T instance, Class<T> classType) {
		return new ResultVo<T>(200, instance);
	}

	public static <T> ResultVo<T> success(String msg, Class<T> classType) {
		return new ResultVo<T>(200, msg, null);
	}

	public static <T> ResultVo<T> success(String msg, T instance, Class<T> classType) {
		return new ResultVo<T>(200, msg, instance);
	}

	public static <T> ResultVo<T> fail(String msg, Class<T> classType) {
		return new ResultVo<T>(400, msg, null);
	}

	public static <T> ResultVo<T> fail(String msg, T instance, Class<T> classType) {
		return new ResultVo<T>(400, msg, instance);
	}

	public static <T> ResultVo<T> fail(Integer code, String msg, Class<T> classType) {
		return new ResultVo<T>(code, msg, null);
	}

	public static <T> ResultVo<T> fail(Integer code, String msg, T instance, Class<T> classType) {
		return new ResultVo<T>(code, msg, instance);
	}

	@Override
	public String toString() {
		return "ResultVo{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", data=" + data +
				'}';
	}
}

