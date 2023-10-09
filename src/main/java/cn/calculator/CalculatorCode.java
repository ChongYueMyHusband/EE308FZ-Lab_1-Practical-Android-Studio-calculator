package cn.calculator;

public enum CalculatorCode {


    ZERO {
        @Override
        public char getNumber() {
            return '0';
        }
    },
    ONE {
        @Override
        public char getNumber() {
            return '1';
        }
    },
    TWO {
        @Override
        public char getNumber() {
            return '2';
        }
    },
    THREE {
        @Override
        public char getNumber() {
            return '3';
        }
    },
    FOUR {
        @Override
        public char getNumber() {
            return '4';
        }
    },
    FIVE {
        @Override
        public char getNumber() {
            return '5';
        }
    },
    SIX {
        @Override
        public char getNumber() {
            return '6';
        }
    },
    SEVEN {
        @Override
        public char getNumber() {
            return '7';
        }
    },
    EIGHT {
        @Override
        public char getNumber() {
            return '8';
        }
    },
    NINE {
        @Override
        public char getNumber() {
            return '9';
        }
    },
    DOT {
        @Override
        public char getNumber() {
            return '.';
        }
    };


    public abstract char getNumber();
}
