package com.ivan.spring.mvc.spring_mvc_calculator;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class CalculatorUtil {
    private String[] token;
    private int position;

    public CalculatorUtil(@RequestParam("input") String input) {
        input = input.replaceAll("\\s", ""); // убираем все пробелы
        input = input.replaceAll("\\+\\-", "-"); // заменяем "+-" на "-"
        input = input.replaceAll("(?<=[\\d\\)])\\*", " * "); // добавляем пробелы вокруг оператора "*"
        input = input.replaceAll("(?<=[\\d\\)])\\/", " / "); // добавляем пробелы вокруг оператора "/"
        input = input.replaceAll("(?<=[\\d\\)])\\+", " + "); // добавляем пробелы вокруг оператора "+"
        input = input.replaceAll("(?<=[\\d\\)])\\-", " - "); // добавляем пробелы вокруг оператора "-"
        input = input.replaceAll("(?=[\\d\\(])\\(", "( "); // добавляем пробелы вокруг оператора "("
        input = input.replaceAll("(?<=[\\d\\)])\\)", " )"); // добавляем пробелы вокруг оператора ")"
        this.token = input.trim().split(" ");
        this.position = 0;
    }

    @ResponseBody
    public double calculate() {
        double first = multiply();

        while (position < token.length) {
            String operator = token[position];
            if (!operator.equals("+") && !operator.equals("-")) {
                break;
            } else {
                position++;
            }

            double second = multiply();
            if (operator.equals("+")) {
                first += second;
            } else {
                first -= second;
            }
        }
        return first;
    }

    public double multiply() {

        double first = Staple();
        while (position < token.length) {
            String operator = token[position];
            if (!operator.equals("*") && !operator.equals("/")) {
                break;
            } else {
                position++;
            }

            double second = Staple();
            if (operator.equals("*")) {
                first *= second;
            } else {
                first /= second;
            }
        }
        return first;
    }

    public double Staple(){
        String next = token[position];
        double result;
        if (next.equals("(")){
            position++;
            result = calculate();
            String closingBracket;
            if (position < token.length){
                closingBracket = token[position];
            } else {
                throw new IllegalArgumentException("Неправильное выражение");
            }
            if (closingBracket.equals(")")){
                position++;
                return result;
            }
            throw new IllegalArgumentException("')' отcутствует скобка " +  closingBracket);

        }
        position++;
        return Double.parseDouble(next);
    }
}
