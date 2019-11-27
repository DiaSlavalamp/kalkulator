package com.lebedev.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Collections;
import java.util.List;

@PropertySource(value = "classpath:advanced.properties")
@Controller
public class RequestController {


    @Autowired
    private UserRepository userRepository;

    @Value("${advanced.captcha}")
    private Boolean captchaON;

    private Captcha actualCaptcha;

    @GetMapping("/index")
    public String get(Model model) {
        baseReturn(model);
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String request(@RequestParam String userExp, String userCaptcha, Model model) {
        if (captchaON) {
            calculation(model, userExp, userCaptcha);
        } else {
            calculation(model, userExp);
        }
        baseReturn(model);
        return "index";
    }

    private Model baseReturn(Model model) {
        Iterable<Expression> expsBase = userRepository.findAll();
        Collections.reverse((List) expsBase);
        model.addAttribute("expsBase", expsBase);
        if (captchaON) {
            actualCaptcha = new Captcha();
            model.addAttribute("captcha", actualCaptcha);
            model.addAttribute("captchaStatus", "капча включена");
            model.addAttribute("captchaSize", actualCaptcha.getSize());
            model.addAttribute("captchaImage", actualCaptcha.getBase64Image());
        }else {
            model.addAttribute("captchaStatus", "капча выключена");
        }
        return model;
    }

    private Model calculation(Model model, String userExp, String userCaptcha) {
        if (userCaptcha.equals(actualCaptcha.toString())) {
            userRepository.save(new Expression(userExp));
            model.addAttribute("message", "Операция выполнена успешно!");
        } else {
            model.addAttribute("message", "Код с картинки введен неверно!");
            model.addAttribute("returnedUserExp",userExp);
        }
        return model;
    }

    private Model calculation(Model model, String userExp) {
        userRepository.save(new Expression(userExp));
        model.addAttribute("message", "Операция выполнена успешно!");
        return model;
    }


}