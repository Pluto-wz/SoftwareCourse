package com.wz;

public class Calculate {
    // 判断是否为操作符号
    public static boolean isOperator(String operator) {
        if (operator.equals("+") || operator.equals("-")
                || operator.equals("*") || operator.equals("/")
                || operator.equals("(") || operator.equals(")"))
            return true;
        else
            return false;
    }

    // 设置操作符号的优先级别
    public static int priority(String operator) {
        if (operator.equals("+") || operator.equals("-"))
            return 1;
        else if (operator.equals("*") || operator.equals("/"))
            return 2;
        else
            return 0;
    }

    public static String getResult(String operator, String operandString1, String operandString2) {
        try {
            String[] parts1 = operandString1.split("/");
            String[] parts2 = operandString2.split("/");

            Fraction fraction1 = parts1.length == 2 ? new Fraction(Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1])) : new Fraction(Integer.parseInt(parts1[0]), 1);
            Fraction fraction2 = parts2.length == 2 ? new Fraction(Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1])) : new Fraction(Integer.parseInt(parts2[0]), 1);

            Fraction result = null;

            if (operator.equals("+")) {
                result = fraction2.add(fraction1);
            } else if (operator.equals("-")) {
                result = fraction2.subtract(fraction1);
            } else if (operator.equals("*")) {
                result = fraction2.multiply(fraction1);
            } else if (operator.equals("/")) {
                result = fraction2.divide(fraction1);
            }

            if (result == null || result.getNumerator()<0) return "error";
            return result.toString();
        } catch (NumberFormatException e) {
            return "error";
        } catch (ArithmeticException e) {
            return "error"; // 处理除以零的情况
        }
    }
}
