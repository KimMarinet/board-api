package org.koreait.member.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.member.controllers.RequestJoin;
import org.koreait.member.services.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
@ActiveProfiles({"default", "test"})
public class TokenServiceTest {

    @Autowired
    private JoinService joinService;

    @Autowired
    private TokenService tokenService;

    @BeforeEach
    void init(){

        RequestJoin form = new RequestJoin();

        form.setEmail("user01@test.org");
        form.setPassword("_aA123456");
        form.setConfirmPassword("_aA123456");
        form.setMobile("01011111111");
        form.setName("user01");
        form.setTermsAgree(true);

        joinService.process(form);
    }

    @Test
    void jwtCreationTest(){
        String token = tokenService.create("user01@test.org");
        System.out.println(token);

        assertNotNull(token);
    }
}
