package cn.calculator;

public enum CalculatorOperator {
    CLEAR,
    REVERSE ,
    PERCENT ,
    DIVISION {
        @Override
        public char getOperator() {
            return '÷';
        }
    },
    MULTIPLICATION {
        @Override
        public char getOperator() {
            return '×';
        }
    },
    SUBTRACTION {
        @Override
        public char getOperator() {
            return '-';
        }
    },
    ADDITION {
        @Override
        public char getOperator() {
            return '+';
        }
    },
    POWER{
        @Override
        public char getOperator() {
            return '^';
        }
    },
    SIN,
    COS,
    TAN,

    EQUAlS;



    public char getOperator() {
        return 0;
    }
}
