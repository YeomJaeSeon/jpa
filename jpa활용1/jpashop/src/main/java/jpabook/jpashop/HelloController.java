package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!!");
        // 뷰템프릿 HTML response 바디에 넣어서 내려준다.viewResolver로 물리적인이름으로 바뀐디ㅜ model의 데이터를 view로전달하고 해당 view(thymeleaf)를 응답 바디 에넣어서내려줌
        return "hello";
    }
}
