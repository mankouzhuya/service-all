package com.github.zyj.serviceall.utils;


import com.github.zyj.serviceall.enums.ErrorEnums;
import com.github.zyj.serviceall.resp.ResponseDTO;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseUtil {

    public static <T> ResponseDTO success(T data) {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.setCode(HttpStatus.OK.value());
        responseDTO.setData(data);
        return responseDTO;
    }

    public static <T> ResponseDTO successWithMsg(String msg) {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.setCode(HttpStatus.OK.value());
        responseDTO.setMsg(msg);
        return responseDTO;
    }
    public static <T> ResponseDTO success() {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.setCode(HttpStatus.OK.value());
        return responseDTO;
    }

    public static <T> ResponseDTO error(Integer status,String msg) {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.setCode(status);
        responseDTO.setMsg(msg);
        return responseDTO;
    }

    public static <T> ResponseDTO error(ErrorEnums errorEnums) {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.setCode(errorEnums.getCode());
        responseDTO.setMsg(errorEnums.getMsg());
        return responseDTO;
    }


}
