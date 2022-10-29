package org.example;

import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean move = true;
        while (move) {
            System.out.print("Введите формулу, которую хотите вычеслить:" + "\n" + "  формула может содержать" + "\n" + "    a)переменные (a,b,c,... и тд.)" + "\n" + "    б)функции (sinx,cosx,tgx,ctgx,x^n,e^x,logx)" + "\n");
            System.out.print("f(x)= ");
            String f =scan.nextLine();
            Formula formula = new Formula();
            formula.setFormula(f);
            if(formula.isCorrect()) {
                System.out.print("Формула: f(x)=" + f + "\n");


                String res = formula.calculateFormula();
                System.out.print("Результат вычисления: x=" + res + "\n");
            }
            else System.out.print("Неправильная постановка формулы!"+"\n");
            System.out.print("Хотите произести подсчет еще одной формулы? (y/n)"+"\n");
            String s=scan.nextLine();
            if(s.equals("n"))  move=false;
        }
    }
}