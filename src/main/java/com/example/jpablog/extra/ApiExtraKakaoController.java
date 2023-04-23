package com.example.jpablog.extra;

import com.example.jpablog.common.model.ResponseResult;
import com.example.jpablog.extra.model.KakaoTranslateInput;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
public class ApiExtraKakaoController {

    @Value("${KAKAO-API-KEY}")
    private String kakao_api_key;

    @Value("${KAKAO-URL}")
    private String kakao_url;

    /**
     * 90. KAKAO OPEN API를 활용한 게시글 번역서비스를 구현하는 API를 작성해 보세요.
     * - 카카오 개발자사이트에 앱을 통해 가입한 이후에 진행
     */
    @GetMapping("/api/extra/kakao/translate")
    public ResponseEntity<?> translate(@RequestBody KakaoTranslateInput kakaoTranslateInput){

        String restApiKey = kakao_api_key;
        String url = kakao_url;


        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("src_lang", "kr");
        parameters.add("target_lang", "en");
        parameters.add("query", kakaoTranslateInput.getText());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "KakaoAK " + restApiKey);

        HttpEntity formEntity = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, formEntity, String.class);


        return ResponseResult.success(responseEntity.getBody());
    }



}
