package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.lang.Math;
import java.lang.Exception;

import java.util.*;

public class Formula {
    private String formula;

    public void setFormula(String formula) {
        this.formula = formula;
    }


    /**
     * Метод проверяет правильность постановок скобок
     *
     * @return true в случае правильной постановке скобок
     */
    private boolean isCorrectBrackets() {
        boolean rezult = true;
        int size = formula.length();
        Character word;
        Stack<Character> formula_stack = new Stack<>();
        int i = 0;

        while (size > 0 && rezult) {
            word = formula.charAt(i);
            if (word.equals('(')) formula_stack.push(word);
            else {
                if (word.equals(')') && formula_stack.size() > 0 && formula_stack.peek().equals('('))
                    formula_stack.pop();
                else {
                    if (word.equals(')') && formula_stack.size() > 0 && formula_stack.peek().equals(')'))
                        rezult = false;
                    if (word.equals(')') && formula_stack.size() == 0) rezult = false;
                }
            }
            size--;
            i++;
        }
        return rezult && formula_stack.isEmpty();
    }

    /**
     * Метод проверяет правильную постановку скобок для корректного подсчета формулы (кореектность формулы трактуется следующим образом: формула должна иметь вид ( (elem oper elem) oper (elem oper elem) ) - каждая операция между двумя элементами заключена в скобки)
     *
     * @return истину в случае корректной постановки формулы
     */
    private boolean isCheckView() {
        Character word;
        int size = formula.length();
        int i = 0;
        int sk = -1;
        Vector<Integer> v = new Vector<>(size);
        for (int k = 0; k < size; k++) {
            v.add(k, 0);
        }
        boolean move = true;
        while (size > 0 && move) {
            word = formula.charAt(i);
            if (word.equals('(')) {
                sk++;
            }
            else {
                if(sk!=-1) {
                    if (Character.isDigit(word)) {
                        int tmp = v.get(sk);
                        tmp++;
                        v.set(sk, tmp);
                        i++;
                        size--;
                        word = formula.charAt(i);
                        while (size > 0 && Character.isDigit(word)) {

                            i++;
                            size--;
                            word = formula.charAt(i);
                        }
                        i--;
                        size++;
                    } else {
                        if (Character.isLetter(word)) {
                            int tmp = v.get(sk);
                            tmp++;
                            v.set(sk, tmp);
                            i++;
                            size--;
                            word = formula.charAt(i);
                            while (size > 0 && (Character.isLetter(word)||word.equals('^'))) {
                                i++;
                                size--;
                                word = formula.charAt(i);
                            }
                            i--;
                            size++;
                        } else {
                            if (word.equals(')')) {
                                int tmp = v.get(sk);
                                if (tmp != 2) move = false;
                                else {
                                    v.set(sk, 0);
                                    sk--;
                                    if (sk != -1) {
                                        tmp = v.get(sk);
                                        tmp++;
                                        v.set(sk, tmp);
                                    }

                                }
                            }
                        }
                    }
                }
                else move=false;
            }
            size--;
            i++;
        }
        return move;
    }

    /**
     * Метод проверяет корректностьь формулы
     * @return истина в случае правильной записи формулы
     */
    public boolean isCorrect() {
        return isCorrectBrackets() && isCheckView();
    }

    /**
     * Метод проверяет является ли набор символов формулой
     * @param func строка, которую необходимо проверить
     * @return истину в случае,если наборсимволов-формула
     */
    private boolean checkFunc(String func) {
        if (func.equals("sinx") || func.equals("cosx") || func.equals("tgx") || func.equals("ctgx") || func.equals("x^n") || func.equals("logx") || func.equals("e^x"))
            return true;
        else return false;
    }

    /**
     * Метод подсчитывает значение формулы, подставляя в нее перемееную, значение которой вводит пользователь
     * @param func строка, которая является формулой
     * @return вычисленное значение
     */
    private double countFunc(String func)  {
        boolean move=true;
        Scanner scan = new Scanner(System.in);
        Double x;
        System.out.print("Введите неизвестную переменную x :");
        x=scan.nextDouble();

            double res = 0;
            switch (func) {
                case "sinx": {
                    res = Math.sin(x);
                    break;

                }
                case "cosx": {
                    res = Math.cos(x);
                    break;
                }
                case "tgx": {
                    res = Math.tan(x);
                    break;
                }
                case "ctgx": {
                    res = 1 / Math.tan(x);
                    break;

                }
                case "x^n": {
                    double n;
                    System.out.print("Введите неизвестную переменную n :");
                    n = scan.nextInt();
                    res = Math.pow(x, n);
                    break;

                }
                case "logx": {
                    res = Math.log(x);
                    break;

                }
                case "e^x": {
                    res = Math.exp(x);
                    break;

                }
            }
            return res;
        }

    /**
     * Метод преобразует букву(переменную) из строкового в числовой вид, запрашивая данную переменную у пользователя
     * @param letter буква, которую необходимо преобразовать
     * @return результат преобразования(число)
     */
    private double convertLetter(String letter) {
        Scanner scan = new Scanner(System.in);
        double result = 0;
        Character character = letter.charAt(0);
        System.out.print("Введите неизвестную переменную " + letter + ":");
        result = scan.nextDouble();
        return result;
    }

    /**
     * Метод преобразует число из строкового в числовой вид
     * @param digit
     * @return
     */
    private double convertDigit(String digit)
    {
        double result = 0;
        result=Double.parseDouble(digit);
         return result;

    }

    /**
     * Метод переводит заданную формулу в постфиксную форму
     *
     * @return представленее заданной формулы в постфиксном виде
     */
    public Queue<String> postfixFormula() {
        int size = formula.length();
        int check = 0;
        Character character;
        String number = "";
        String func = "";
        String res = "";
        Stack<String> rezult_stack = new Stack<String>();
        Queue<String> rezult = new LinkedList<String>();
        int i1 = 0;
        while (size > 0) {
            character = formula.charAt(i1);

            if (Character.isLetter(character)) {
                while ((size > 0) && ((Character.isLetter(character))||character.equals('^'))) {
                    String a = String.valueOf(character);
                    func += a;
                    check++;
                    size--;
                    i1++;
                    if (size > 0) character = formula.charAt(i1);
                }
                size++;
                i1--;

                if (check == 1)
                    res = String.valueOf(convertLetter(func));
                else if (checkFunc(func)) {
                    double res_ = countFunc(func);
                    res = String.valueOf(res_);
                }
                rezult.add(res);

            } else if (Character.isDigit(character)) {
                while ((size > 0) && (Character.isDigit(character))) {
                    number += character;

                    size--;
                    i1++;
                    if (size > 0) character = formula.charAt(i1);
                }
                size++;
                i1--;

                rezult.add(String.valueOf(convertDigit(number)));
                number = "";


            } else {
                if (!character.equals(')')) {
                    rezult_stack.push(String.valueOf(character));

                } else {

                    String tmp = rezult_stack.peek();

                    while (!tmp.equals("(")) {
                        rezult.add(tmp);
                        rezult_stack.pop();
                        tmp = rezult_stack.peek();
                    }
                    rezult_stack.pop();

                }
            }
            size--;
            i1++;
        }
        while (!rezult_stack.isEmpty()) {
            String tmp = rezult_stack.peek();
            rezult.add(tmp);
            rezult_stack.pop();
        }
        return rezult;
    }

    /**
     * Метод вычисляет операцию
     * @param a  первый операнд в выражении
     * @param b  второй операнд в выражении
     * @param op оперция
     * @return результат вычисления операции
     */
    private double calculate(String a, String b, String op) {

        double oper1 = convertDigit(a);
        double oper2 = convertDigit(b);
        double result = 0;
        switch (op) {
            case "+": {
                result = oper1 + oper2;
                break;
            }
            case "-": {
                result = oper1 - oper2;
                break;
            }
            case "*": {
                result = oper1 * oper2;
                break;
            }
            case "/": {
                result = oper1 / oper2;
                break;
            }
            default: {
                break;
            }
        }
        return result;
    }

    /**
     * Метод подсчитывает значение формулы
     * @return результат вычисления формулы
     */

    public String calculateFormula() {
            String word = "";
            Stack<String> q = new Stack<String>();
            Queue<String> tmp = new LinkedList<String>();

            tmp = postfixFormula();
            double result = 0;

            while (!tmp.isEmpty()) {
                Character character = 0;
                word = tmp.peek();
                tmp.remove();
                Character ch = word.charAt(0);
                if (word.length() > 1) {
                    character = word.charAt(1);
                }


                if (Character.isDigit(ch) || ch.equals('0')) q.add(word);
                else {
                    if (ch.equals('-') && Character.isDigit(character)) q.add(word);
                    else {
                        if (ch.equals('-') && character.equals('0')) q.add(word);
                        else {
                            String oper2 = q.peek();
                            q.pop();
                            String oper1 = q.peek();
                            q.pop();
                            result = calculate(oper1, oper2, word);
                            String res = Double.toString(result);
                            q.add(res);
                        }
                    }
                }
            }
            return q.peek();
        }
    }

