package com.example.jpablog.extra;

import com.example.jpablog.common.model.ResponseResult;
import com.example.jpablog.extra.model.AirInput;
import com.example.jpablog.extra.model.KakaoTranslateInput;
import com.example.jpablog.extra.model.OpenApiResult;
import com.example.jpablog.extra.model.PharmacySearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class ApiExtraKakaoController {

    /**
     * 90. KAKAO OPEN API를 활용한 게시글 번역서비스를 구현하는 API를 작성해 보세요.
     * - 카카오 개발자사이트에 앱을 통해 가입한 이후에 진행
     */
    @GetMapping("/api/extra/kakao/translate")
    public ResponseEntity<?> translate(@RequestBody KakaoTranslateInput kakaoTranslateInput){

        return ResponseResult.success(kakaoTranslateInput);
    }



}
