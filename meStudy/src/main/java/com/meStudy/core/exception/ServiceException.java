package com.meStudy.core.exception;

/**
 * @Author
 * @Description 服务异常
 * @Date
 * @Param
 * @return
 **/
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

}
