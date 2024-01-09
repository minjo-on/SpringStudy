package com.example.demo.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class CalcForm {
    @NotNull(message = "왼쪽 : 숫자를 입력해주세요")
    @Range(min = 1,max = 10,message = "왼쪽 : {min}~{max} 범위의 숫자를 입력해주세요.")
    private Integer leftNum;

    @NotNull(message = "오른쪽 : 숫자를 입력해주세요")
    @Range(min = 1,max = 10,message = "오른쪽 : {min}~{max} 범위의 숫자를 입력해주세요.")
    private Integer rightNum;

}
