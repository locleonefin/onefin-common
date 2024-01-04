package com.onefin.ewallet.common.base.anotation;

import com.google.common.base.Joiner;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchEmailValidator implements ConstraintValidator<MatchEmail,String> {
    private boolean checkNull;
    private static final String PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null){
            return !checkNull;
        }
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()) return true;

        return false;
    }

    @Override
    public void initialize(MatchEmail constraintAnnotation) {
        this.checkNull = constraintAnnotation.checkNull();
    }
}
