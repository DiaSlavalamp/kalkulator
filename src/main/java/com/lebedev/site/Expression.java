package com.lebedev.site;

import net.objecthunter.exp4j.ExpressionBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Expression {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String exp;
    private String answer;

    public Expression() {
    }

    public Expression(String exp) {
        this.exp = exp;
        //считаем уравнение с помощью exp4j и преобразуем в строку
        String answerD = Double.toString(new ExpressionBuilder(exp).build().evaluate());
        //отбрасываем .0
        if (answerD.substring(answerD.length() - 2).equals(".0")) {
            answer = answerD.substring(0, answerD.length() - 2);
        }else {
            answer = answerD;
        }
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getExp() {
        return exp;
    }
    public void setExp(String exp) {
        this.exp = exp;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
