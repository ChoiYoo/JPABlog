package com.example.jpablog.extra;

import com.example.jpablog.common.model.ResponseResult;
import com.example.jpablog.extra.model.KakaoTranslateInput;
import com.example.jpablog.extra.model.NaverTranslateInput;
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
public class ApiExtraNaverController {
    @Value("${NAVER-CLIENT-ID}")
    private String naver_client_id;

    @Value("${NAVER-CLIENT-SECRET}")
    private String naver_client_secret;

    @Value("${NAVER-URL}")
    private String naver_url;
    /**
     * 91. NAVER OPEN API를 활용한 게시글 번역서비스를 구현하는 API를 작성해 보세요.
     */
    @GetMapping("/api/extra/naver/translate")
    public ResponseEntity<?> translate(@RequestBody NaverTranslateInput naverTranslateInput){
        String clientId = naver_client_id;
        String clientSecret = naver_client_secret;
        String url = naver_url;


        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("source", "ko");
        parameters.add("target", "en");
        parameters.add("text", naverTranslateInput.getText());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-Naver-Client-Id", clientId);
        headers.add("X-Naver-Client-Secret", clientSecret);

        HttpEntity formEntity = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, formEntity, String.class);

        return ResponseResult.success(responseEntity.getBody());
    }
}
