package com.example.demo.dto;

import org.hibernate.validator.constraints.Length;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class MemberDTO {

    @Pattern(regexp = "(?=^[A-za-z])(?=.+\\d[A-Za-z])", message = "아이디는 영대소문자,특수문자,숫자를 포함하어 12자리 입니다")
    @NotBlank(message = "똑바로 써라")
    private String userid;
    @NotBlank(message = "핫 산")
    private String password;

    @NotBlank(message = "메일을 쓰세요")
    @Email(message = "장난치지말고 똑바로 쓰세요")
    private String email;

    // @Length(min = 2, max = 5)
    @Pattern(regexp = "^[가-힣]{2,5}$", message = "이름은 2~5자리로 입력해야합니다.")
    private String name;

    @NotNull
    @Min(value = 0, message = "신생아 가입불가")
    @Max(value = 140, message = "내 나이가 어때서")
    private Integer age;

    private boolean check;

}
