package com.example.mongodb.common.rest;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * <pre>
 *      ResultBean
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/08/10 17:07  修改内容:
 * </pre>
 */
@Data
public class ResultBean<T> {

	/**
	 * 状态
	 * */
	private int status;
	/**
	 * 描述
	 * */
	private String desc;
	/**
	 * 数据返回
	 * */
	private T data;

	public ResultBean(int status, String desc, T data) {
		this.status = status;
		this.desc = desc;
		this.data = data;
	}

	public ResultBean(T data) {
		this.status = HttpStatus.OK.value();
		this.desc = "处理成功";
		this.data = data;
	}

	public static <T> ResultBean<T> ok(T data) {
		return new ResultBean(data);
	}

	public static <T> ResultBean<T> ok() {
		return new ResultBean(null);
	}

	public static <T> ResultBean<T> badRequest(String desc,T data) {
		return new ResultBean(HttpStatus.BAD_REQUEST.value(), desc, data);
	}

	public static <T> ResultBean<T> badRequest(String desc) {
		return new ResultBean(HttpStatus.BAD_REQUEST.value(), desc, null);
	}

	public static <T> ResultBean serverError(String desc, T data){
		return new ResultBean(HttpStatus.INTERNAL_SERVER_ERROR.value(),"服务器内部异常:"+desc,data);
	}

	public static <T> ResultBean serverError(String desc){
		return new ResultBean(HttpStatus.INTERNAL_SERVER_ERROR.value(),"服务器内部异常:"+desc,null);
	}

}