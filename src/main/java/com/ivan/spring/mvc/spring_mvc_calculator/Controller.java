package com.ivan.spring.mvc.spring_mvc_calculator;

import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/")
    public String Controller(){
        return "calculator_form";
    }

        @PostMapping("/result.html")
    public String result(@RequestParam("input") String input, Model model) {
            CalculatorUtil calculatorUtil = new CalculatorUtil(input);
            double result = calculatorUtil.calculate();
            model.addAttribute("result", result);
        return "result";
    }
}
