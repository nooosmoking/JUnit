package edu.school21.numbers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


public class NumberWorkerTest {
    NumbersWorker worker;

    @BeforeEach
    void beforeTests() {
        worker = new NumbersWorker();
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 101, 2803})
    public void isPrimeForPrimes(int num) {
        assertTrue(worker.isPrime(num));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 55, 1000000000})
    public void isPrimeForNotPrimes(int num) {
        assertFalse(worker.isPrime(num));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1234,10",
            "55555,25",
            "3476,20",
            "1111,4",
            "-1234,10",
            "999999,54",
            "-86425,25",
            "884,20",
            "48484,28",
            "511546,22",
            "9863,26",
            "-50005,10",
            "88888,40",
            "10019,11"
    }, ignoreLeadingAndTrailingWhitespace = true)
    public void sum(int input, int result) {
        assertEquals(worker.digitSum(input), result);
    }
}
