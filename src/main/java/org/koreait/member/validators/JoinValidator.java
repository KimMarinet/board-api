package org.koreait.member.validators;

import lombok.RequiredArgsConstructor;
import org.koreait.global.validators.MobileValidator;
import org.koreait.global.validators.PasswordValidator;
import org.koreait.member.controllers.RequestJoin;
import org.koreait.member.repositories.MemberRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Lazy
@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, PasswordValidator, MobileValidator {
    private final MemberRepository repository;


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestJoin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        RequestJoin form = (RequestJoin) target;

        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();

        // 1. 아이디 중복 여부
        if (repository.existsByEmail(form.getEmail())) {
            errors.rejectValue("userId", "Duplicated");
        }

        // 2. 비밀번호 복잡성
        if (!checkAlpha(password, false) || !checkNumber(password) || !checkSpecialChars(password)) {
            errors.rejectValue("password", "Complexity");
        }

        // 3. 비밀번호 확인
        if (!password.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "Mismatch");
        }

        if(repository.existsByEmail(form.getEmail())){
            errors.rejectValue("email", "Duplicated");
        }
    }
}
