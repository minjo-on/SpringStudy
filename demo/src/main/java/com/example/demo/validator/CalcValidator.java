package com.example.demo.validator;

import com.example.demo.form.CalcForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class CalcValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CalcForm.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        CalcForm form = (CalcForm) target;

        if(form.getLeftNum() != null && form.getRightNum() !=null){
            if(!((form.getLeftNum()%2==1)&&(form.getRightNum()%2==0))){
                errors.reject("com.example.demo.validator.CalcValidator.message");
            }
        }

    }
}
