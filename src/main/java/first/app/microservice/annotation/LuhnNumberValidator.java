package first.app.microservice.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LuhnNumberValidator implements ConstraintValidator<Luhn , String>  {
    @Override
    public void initialize(Luhn luhn) {
    }

    @Override
    public boolean isValid(String number, ConstraintValidatorContext cxt) {
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--)
        {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate)
            {
                n *= 2;
                if (n > 9)
                {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum%10 == 0) ;
    }


}
