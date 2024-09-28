package com.wz;

import java.util.*;
public class Expression {

    private final ArrayList middleExpression = new ArrayList();// 存储中序表达式

    private final ArrayList rightExpression = new ArrayList();// 存储右序表达式

    private String expResult ;// 结果


    public Expression() {

    }

    public String getExpResult() {

        return expResult;
    }


    // 依据输入信息创建对象，将数值与操作符放入中序表达式
    Expression(String formula) {
        StringTokenizer st = new StringTokenizer(formula, "+-*/()", true);
        while (st.hasMoreElements()) {
            String element =st.nextToken();
            middleExpression.add(element);
        }
    }
    //将中序表达式转换为右序表达式
    private void changeRightExpression() {
        Stack stack = new Stack();
        String operator;
        int position = 0;
        while (position < middleExpression.size()) {
            String current = (String) middleExpression.get(position);

            if (Calculate.isOperator(current)) {
                if (stack.isEmpty() || current.equals("(")) {
                    stack.push(current);
                } else {
                    if (current.equals(")")) {
                        while (!stack.isEmpty() && !((String) stack.peek()).equals("(")) {
                            operator = (String) stack.pop();
                            rightExpression.add(operator);
                        }
                        if (!stack.isEmpty()) {
                            stack.pop();
                        }
                    } else {
                        while (!stack.isEmpty() &&
                                Calculate.priority(current) <= Calculate.priority((String) stack.peek())) {
                            operator = (String) stack.pop();
                            if (!operator.equals("(")) {
                                rightExpression.add(operator);
                            }
                        }
                        stack.push(current);
                    }
                }
            } else {
                rightExpression.add(current);
            }
            position++;
        }

        while (!stack.isEmpty()) {
            operator = (String) stack.pop();
            if (!operator.equals("(")) {
                rightExpression.add(operator);
            }
        }
    }

    // 对右序表达式进行求值
    boolean getResult() {

        this.changeRightExpression();

        Stack aStack = new Stack();
        String operand1, operand2, operator = null;
        String result="";
        Iterator iterator = rightExpression.iterator();
        while (iterator.hasNext()) {
            operator = (String) iterator.next();
            if (Calculate.isOperator(operator)) {
                operand1 = (String) aStack.pop();
                operand2 = (String) aStack.pop();
                result = Calculate.getResult(operator, operand1, operand2);
                if(result.equals("error")){
                    return false;
                }
                aStack.push(result);
            } else
                aStack.push(operator);
        }

        expResult = (String)aStack.pop();

        iterator = middleExpression.iterator();

        while (iterator.hasNext()) {

            String resultStr = (String) iterator.next();

            System.out.print(resultStr);

        }

        System.out.println("=" ); //expResult为计算结果
        return true;
    }
}
