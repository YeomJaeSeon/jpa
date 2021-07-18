package jpabook.jpashop.tester.validationtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class ValidationController {

    @PostMapping("/validate")
    public String getMemberData(@Valid @RequestBody MemberDTO memberDTO, BindingResult result){
        if(result.hasErrors()){
            for (FieldError err : result.getFieldErrors()) {
                log.info("error : {}", err.getDefaultMessage());
            }
            return "not property data";
        }

        log.info("name:{}, email:{}", memberDTO.getName(), memberDTO.getEmail());
        return "검증성공";
    }
}
