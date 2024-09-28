package com.wz;

public class Fraction {

    private int numerator; // 分子
    private int denominator; // 分母

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        simplify(); // 简化分数
    }

    //获取分子，保证计算过程中不出现负数
    public int getNumerator(){
        return this.numerator;
    }

    // 简化分数
    private void simplify() {
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        if (denominator < 0) { // 确保分母为正
            numerator = -numerator;
            denominator = -denominator;
        }
    }

    // 计算最大公约数
    private int gcd(int a, int b) {
        return b == 0 ? Math.abs(a) : gcd(b, a % b);
    }

    // 加法
    public Fraction add(Fraction other) {
        return new Fraction(this.numerator * other.denominator + other.numerator * this.denominator, this.denominator * other.denominator);
    }

    // 减法
    public Fraction subtract(Fraction other) {
        return new Fraction(this.numerator * other.denominator - other.numerator * this.denominator, this.denominator * other.denominator);
    }

    // 乘法
    public Fraction multiply(Fraction other) {
        return new Fraction(this.numerator * other.numerator, this.denominator * other.denominator);
    }

    // 除法
    public Fraction divide(Fraction other) {
        if (other.numerator == 0) throw new ArithmeticException("Cannot divide by zero");
        return new Fraction(this.numerator * other.denominator, this.denominator * other.numerator);
    }

    // 转换为字符串
    @Override
    public String toString() {
        if (Math.abs(numerator) >= denominator) { // 处理真分数的情况
            int wholePart = numerator / denominator;
            int newNumerator = Math.abs(numerator % denominator);
            if (newNumerator == 0) {
                return Integer.toString(wholePart);
            } else {
                return wholePart + "'" + newNumerator + "/" + denominator;
            }
        }
        return numerator + "/" + denominator;
    }
}
