package com.wz;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Arithmetic {
    public static ArrayList<Integer> correct = new ArrayList<Integer>();
    public static ArrayList<Integer> wrong = new ArrayList<Integer>();

    public static ArrayList<String> correctAnswerList = new ArrayList<String>();
    public static ArrayList<String> userAnswerList = new ArrayList<String>();
    static int count;
    static int max;

    public static void main(String[] args) throws NullPointerException {
        // 输出面板
        System.out.println("\n---------------------四则运算程序程序---------------------");
        System.out.println("  **       -n    控制生成题目的个数        ");
        System.out.println("  **       -r    参数控制题目中数值范围，不包括该数（必填且大于等于1）   ");
        System.out.println("-----------------------------------------------------------");
        //生成题目和计算答案
        exercisesAndAnswers();
        System.out.println("已生成生成" + count + "道题目：");
        //用户输入答案
        userAnswer();

        matchAnswer();

    }

    public static void exercisesAndAnswers(){

        System.out.print("请输入生成题目个数：");

        count = new Scanner(System.in).nextInt();

        System.out.print("请输入数最大数值：");
        Scanner r = new Scanner(System.in);
        max = r.nextInt();


        //读取答案的txt文件
        File answerFile = new File("./Answers.txt");
        FileOutputStream answerFops = null;

        try {
            answerFops = new FileOutputStream(answerFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintStream answerPS = new PrintStream(answerFops);

        //读取题目txt文件
        File exercisesFile = new File("./Exercises.txt");
        FileOutputStream exercisesFops = null;

        try {
            exercisesFops = new FileOutputStream(exercisesFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintStream exercisesPS = new PrintStream(exercisesFops);

        int i = 0;
        while (i < count) {
            //生成算式
            String formula = getFormula();

            //创建Expression对象
            Expression express = new Expression(formula);

            //先把产生的算式从中序表达式转为右序表达式，再求值
            boolean isCorrect = express.getResult();
            if (isCorrect) {
                //在answer和题目文件中写入生成的
                String result = express.getExpResult();
                correctAnswerList.add(result);
                exercisesPS.println(formula + "=" + express.getExpResult());
                answerPS.print((i + 1) + ".     ");
                answerPS.println(result);
                i++;
            }
        }

    }

    //随机产生算式
    public static String getFormula() {

        Random r = new Random();
        ArrayList<Integer> numList = new ArrayList<Integer>();
        ArrayList<String> express = new ArrayList<String>();
        String[] operator = new String[]{"+", "-", "*", "/"};

        //产生随机数
        for (int i = 0; i < 3; i++) {
            numList.add(r.nextInt(max));
        }

        //将数组中的数字转换成字符串
        int size = numList.size();
        String[] number = new String[size];
        for (int i = 0; i < numList.size(); i++) {
            number[i] = String.valueOf(numList.get(i));
        }

        //在数字中穿插运算符放进express
        StringBuilder exp = new StringBuilder();
        for (int j = 0; j < number.length; j++) {
            express.add(number[j]);
            express.add(operator[r.nextInt(4)]);
        }

        //转成字符串输出
        for (int i = 0; i < express.size() - 1; i++)
            exp.append(express.get(i));
        return exp.toString();
    }

    public static void userAnswer() {
        int i;
        System.out.println("请输入答案：");
        Scanner scanner = new Scanner(System.in);
        for (i = 0; i < count; i++) {
            //用户把答案输入并存入数组
            System.out.print("您的第" + (i+1) + "题答案为：");
            userAnswerList.add(scanner.next());
        }
        System.out.println("用户答案：");
        for (i = 0; i < count; i++) {
            System.out.print((i + 1) + ".     ");
            System.out.println(userAnswerList.get(i));
        }
    }

    public static void matchAnswer() {

        //比对答案是否相同，写入grade文件中
        File file = new File("./Grade.txt");
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintStream ps = new PrintStream(fileOutputStream);
        int i=0;

        if (correctAnswerList.size() != 0 && userAnswerList.size() != 0) {

            int index = 1;

            for (i = 0; i < count; i++,index++) {

                if (correctAnswerList.get(i).equals(userAnswerList.get(i)) ){
                    correct.add(index);
                    System.out.println("第" + (i+1) + "题正确");
                } else {
                    wrong.add(index);
                    System.out.println("第" + (i+1) + "题错误");
                }


            }
            ps.print("Correct:" + correct.size() + " (");
            for (i = 0; i < correct.size(); i++) {
                if (i != 0) ps.print("，");
                ps.print(correct.get(i));
            }
            ps.println(")");

            ps.print("Wrong:" + wrong.size() + " (");
            for (i = 0; i < wrong.size(); i++) {
                if (i != 0) ps.print("，");
                ps.print(wrong.get(i));
            }
            ps.print(")");

        }
    }
}
