package com.example.springcloud.gateway.bean;

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
public class ResultBean {

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
	private Object data;

	public ResultBean(int status, String desc, Object data) {
		this.status = status;
		this.desc = desc;
		this.data = data;
	}

	public ResultBean(Object data) {
		this.status = HttpStatus.OK.value();
		this.desc = "处理成功";
		this.data = data;
	}

	public static ResultBean ok(Object data) {
		return new ResultBean(data);
	}

	public static ResultBean ok() {
		return new ResultBean(null);
	}

	public static ResultBean badRequest(String desc,Object data) {
		return new ResultBean(HttpStatus.BAD_REQUEST.value(), desc, data);
	}

	public static ResultBean badRequest(String desc) {
		return new ResultBean(HttpStatus.BAD_REQUEST.value(), desc, null);
	}

	public static ResultBean serverError(String desc, Object data){
		return new ResultBean(HttpStatus.INTERNAL_SERVER_ERROR.value(),"服务器内部异常:"+desc,data);
	}

	public static ResultBean serverError(String desc){
		return new ResultBean(HttpStatus.INTERNAL_SERVER_ERROR.value(),"服务器内部异常:"+desc,null);
	}

}