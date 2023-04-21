package com.example.jpablog.user.service;

import com.example.jpablog.board.model.ServiceResult;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.*;

import java.util.List;

public interface PointService {

    ServiceResult addPoint(String email, MemberPointInput memberPointInput);

}
