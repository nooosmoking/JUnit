package edu.school21.numbers;

public class NumbersWorker {
    public boolean isPrime(int number) {
        if (number < 2) {
            throw new IllegalNumberException();
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int digitSum(int number) {
        int res = 0;
        if (number < 0) number *= -1;
        while (number > 0) {
            res += number % 10;
            number /= 10;
        }
        return res;
    }
}

class IllegalNumberException extends RuntimeException {
    public IllegalNumberException() {
        super("Number should be more than 1");
    }
}
