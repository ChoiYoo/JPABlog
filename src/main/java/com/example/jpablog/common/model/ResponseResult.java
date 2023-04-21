package com.example.jpablog.common.model;

import com.example.jpablog.board.entity.BoardBadReport;
import com.example.jpablog.board.model.ServiceResult;
import com.example.jpablog.user.model.ResponseMessage;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

public class ResponseResult {


    public static ResponseEntity<?> fail(String message) {
        return ResponseEntity.badRequest().body(ResponseMessage.fail(message));
    }

    public static ResponseEntity<?> success() {
        return success(null);
    }

    public static ResponseEntity<?> success(Object data) {

        return ResponseEntity.ok().body(ResponseMessage.success(data));
    }

    public static ResponseEntity<?> result(ServiceResult result) {

        if(result.isFail()){
            return fail(result.getMessage());
        }
        return success();
    }
}
