import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Stack;

public class Calculator implements Initializable {
    public TextField equationTextField;
    public TextField resultTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void insertSeven(ActionEvent actionEvent) {
        updateView("7");
    }

    public void insertEight(ActionEvent actionEvent) {
        updateView("8");
    }

    public void insertNine(ActionEvent actionEvent) {
        updateView("9");
    }

    public void clearAll(ActionEvent actionEvent) {
        equationTextField.clear();
        resultTextField.clear();
    }

    public void deleteLastSign(ActionEvent actionEvent) {
        equationTextField.deletePreviousChar();
        updateView();
    }

    public void insertFour(ActionEvent actionEvent) {
        updateView("4");
    }

    public void insertFive(ActionEvent actionEvent) {
        updateView("5");
    }

    public void insertSix(ActionEvent actionEvent) {
        updateView("6");
    }

    public void insertMultipleSign(ActionEvent actionEvent) {
        updateView("×");
    }

    public void insetDivideSign(ActionEvent actionEvent) {
        updateView("÷");
    }

    public void insertOne(ActionEvent actionEvent) {
        updateView("1");
    }

    public void insertTwo(ActionEvent actionEvent) {
        updateView("2");
    }

    public void insertThree(ActionEvent actionEvent) {
        updateView("3");
    }

    public void insertPlusSign(ActionEvent actionEvent) {
        updateView("+");
    }

    public void insertMinusSign(ActionEvent actionEvent) {
        updateView("-");
    }

    public void insertZero(ActionEvent actionEvent) {
        updateView("0");
    }

    public void insertComma(ActionEvent actionEvent) {
        updateView(".");
    }

    public void calculateEquation(ActionEvent actionEvent) {
    }

    private void updateView(String sign) {
        String input = equationTextField.getText();
        input = input.concat(sign);
        equationTextField.setText(input);
        calculate(input);
    }

    private void updateView() {
        String input = equationTextField.getText();
        calculate(input);
    }

    private void calculate(String input) {
        String output = calculateRPNValue(convertToRPN(input)).toString();
        resultTextField.setText(output);
    }

    private LinkedList<String> convertToRPN(String input) {
        LinkedList<String> output = new LinkedList<>();
        StringBuilder value = new StringBuilder();
        Stack<String> stack = new Stack<>();
        for (Character sign : input.toCharArray()) {
            if (input.startsWith("-")) {
                value.append("-");
                continue;
            }
            if (Character.isDigit(sign) || sign.equals('.')) {
                value.append(sign);
            } else {
                if (value.length() != 0) {
                    output.addLast(value.toString());
                    value.delete(0, value.length());
                }
                try {
                    switch (sign) {
                        case '(':
                            stack.push("(");
                            break;
                        case '+':
                            while (stack.peek().equals("+") || stack.peek().equals("-") || stack.peek().equals("^") || stack.peek().equals("×") || stack.peek().equals("÷") || stack.peek().equals("%")) {
                                output.addLast(stack.pop());
                            }
                            stack.push("+");
                            break;
                        case '-':
                            while (stack.peek().equals("+") || stack.peek().equals("-") || stack.peek().equals("^") || stack.peek().equals("×") || stack.peek().equals("÷") || stack.peek().equals("%")) {
                                output.addLast(stack.pop());
                            }
                            stack.push("-");
                            break;
                        case ')':
                            while (!stack.peek().equals("(")) {
                                output.addLast(stack.pop());
                            }
                            stack.pop();
                            break;
                        case '÷':
                            while (stack.peek().equals("^") || stack.peek().equals("×") || stack.peek().equals("÷") || stack.peek().equals("%")) {
                                output.addLast(stack.pop());
                            }
                            stack.push("÷");
                            break;
                        case '×':
                            while (stack.peek().equals("^") || stack.peek().equals("×") || stack.peek().equals("÷") || stack.peek().equals("%")) {
                                output.addLast(stack.pop());
                            }
                            stack.push("×");
                            break;
                        case '%':
                            while (stack.peek().equals("^") || stack.peek().equals("×") || stack.peek().equals("÷") || stack.peek().equals("%")) {
                                output.addLast(stack.pop());
                            }
                            stack.push("%");
                            break;
                        case '^':
                            stack.push("^");
                            break;
                    }
                } catch (EmptyStackException e) {
                    stack.push(sign.toString());
                }
            }
        }
        if (value.length() != 0) {
            output.addLast(value.toString());
        }
        while (!stack.empty()) {
            output.addLast(stack.pop());
        }
        return output;
    }

    private Double calculateRPNValue(LinkedList<String> input) {
        Stack<Double> stack = new Stack<>();
        for (String value : input) {
            try {
                stack.push(Double.parseDouble(value));

            } catch (NumberFormatException e) {
                Double num2 = stack.pop();
                Double num1 = stack.pop();
                stack.push(calculateResult(num1, num2, value.toCharArray()[0]));
            }
        }
        return stack.pop();
    }

    private Double calculateResult(Double num1, Double num2, char operator) {
        double result = 0.;
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '÷':
                result = num1 / num2;
                break;
            case '×':
                result = num1 * num2;
                break;
            case '%':
                result = num1 / 100 * num2;
                break;
            case '^':
                result = Math.pow(num1, num2);
                break;
        }
        return result;
    }
}
