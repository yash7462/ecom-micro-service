package com.product.config.anotation;

import com.product.config.ServiceResponse;
import com.product.utils.EcomConstant;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Aspect
@Component
@Order(1)
@NoArgsConstructor
public class TokenValidationInterceptor {

    @Autowired
    private RestTemplate restTemplate;

    @Before("@annotation(ValidateToken)")
    public void handleTokenValidation() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String token = request.getHeader(EcomConstant.AUTH_TOKEN);
        if(StringUtils.isBlank(token)){
            throw new AuthValidationException("Required auth-token is missing");
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Content-Type", "application/json");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set(EcomConstant.AUTH_TOKEN,token);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<ServiceResponse> responseEntity = restTemplate
                .exchange(EcomConstant.AUTH_SERVICE+"validate", HttpMethod.POST, entity, ServiceResponse.class);

        if(!responseEntity.getBody().isStatus()){
            throw new AuthValidationException("Invalid or expired token");
        }

    }


}
