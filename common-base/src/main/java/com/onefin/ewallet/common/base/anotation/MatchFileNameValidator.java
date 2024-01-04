package com.onefin.ewallet.common.base.anotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchFileNameValidator implements ConstraintValidator<MatchFileName,String> {

    private static final String PATTERN = "^[a-zA-Z0-9_ -()]+\\.[a-zA-Z]{1,5}$";
    private boolean checkNull;
    @Override
    public boolean isValid(String fileName, ConstraintValidatorContext constraintValidatorContext) {
        if (fileName == null){
            return !checkNull;
        }
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }

    @Override
    public void initialize(MatchFileName constraintAnnotation) {
        this.checkNull = constraintAnnotation.checkNull();
    }
}
