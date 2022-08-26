package com.github.zyj.serviceall.advice;

import com.github.zyj.serviceall.enums.ErrorEnums;
import com.github.zyj.serviceall.exception.BizException;
import com.github.zyj.serviceall.resp.ResponseDTO;
import com.github.zyj.serviceall.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常出来
 * @author jun
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    public ResponseDTO bizExceptionHandler(BizException e) {
        log.error("发生业务异常！原因是:{}", e.getMsg());
        return ResponseUtil.error(ErrorEnums.BIZ_ERROR);
    }

    /**
     * 处理空指针的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseDTO exceptionHandler(NullPointerException e) {
        log.error("发生空指针异常！原因是:", e);
        return ResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "空异常");
    }


    /**
     * 处理其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseDTO exceptionHandler(Exception e) {
        log.error("未知异常！原因是:{}", e);
        return ResponseUtil.error(ErrorEnums.UNKNOW_ERROR);
    }
}