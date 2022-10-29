package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;


class FormulaTest {
    Formula f= new Formula();

    @BeforeEach
    void setUp() {
        String str="(((5+10)/3)+5)";
        f.setFormula(str);

    }

    @Test
    void testIsCorrectFormula() {
        setUp();
        boolean tmp=f.isCorrect();
        Assertions.assertEquals(tmp,true);
    }

    @Test
    void testIsCorrectFormula_NotCorrect() {
        String str="(5+4)+6*7";
        Formula f1=new Formula();
        f1.setFormula(str);
        boolean tmp=f1.isCorrect();
        Assertions.assertEquals(tmp,false);
    }

    @Test
    void testPostfixFormula() {
        setUp();
        Queue<String> tmp=f.postfixFormula();
        String tmp_s1="";
        String tmp_s2="510+3/5+";
        while(!tmp.isEmpty()) {
            tmp_s1+=tmp.peek();
            tmp.remove();
        }
        Assertions.assertEquals(tmp_s1,tmp_s2);
    }

    @Test
    void testCalculateFormula() {
        setUp();
        String tmp=f.calculateFormula();
        int tmp1 = Integer.parseInt(tmp);
        int tmp2=10;
        Assertions.assertEquals(tmp1,tmp2);
    }
}