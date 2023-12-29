package com.sample.FreeCommunity.user.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
public class SignInFormDTO {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    private String emailInput;

    @NotEmpty(message = "이메일 도메인은 필수 입력 값입니다.")
    private String emailSelect;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String passwordCheck;

    public String getEmail(){
        String email = getEmailInput() + "@" + getEmailSelect();
        return email;
    }
    public void passwordCheck() {
        // 비밀번호 확인 검사
        if (!password.equals(passwordCheck)) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void emailValidation() {
        // 비밀번호 확인 검사
        if (!getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new IllegalStateException("이메일 형식이 아닙니다.");
        }
    }
}
