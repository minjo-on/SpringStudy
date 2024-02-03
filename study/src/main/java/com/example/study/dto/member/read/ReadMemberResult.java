package com.example.study.dto.member.read;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class ReadMemberResult<T> {
    private T data;
    private int count;

}
