package chat.hala;

import org.junit.Test;

/**
 * @author wjy on 2019/9/16/016.
 */
public class 多次方 {
    @Test
    public void addition_isCorrect() {

        double power = Power(-3.0, -2);
        System.out.println(power+"");

    }

    public double Power(double base, int exponent) {

        if (exponent < 0) {
            exponent=-exponent;
            base=1/base;
        }
        if (base == 0) {
            return 0.0d;
        }
        if (exponent == 0) {
            return 1.0d;
        }
            return base * Power(base, exponent - 1);
    }
}
