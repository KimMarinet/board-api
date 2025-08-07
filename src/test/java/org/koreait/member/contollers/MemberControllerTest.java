package org.koreait.member.contollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.koreait.member.controllers.RequestJoin;
import org.koreait.member.controllers.RequestToken;
import org.koreait.member.services.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"default", "test"})
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JoinService joinService;

    @Test
    void joinTest() throws Exception {

        RequestJoin form = new RequestJoin();

        form.setEmail("user01@test.org");
        form.setPassword("_aA123456");
        form.setConfirmPassword("_aA123456");
        form.setMobile("01011111111");
        form.setName("user01");
        form.setTermsAgree(true);

        String body = om.writeValueAsString(form);

        mockMvc.perform(post("/api/v1/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void tekenCreationTest() throws Exception {

        RequestJoin form = new RequestJoin();

        form.setEmail("user01@test.org");
        form.setPassword("_aA123456");
        form.setConfirmPassword("_aA123456");
        form.setMobile("01011111111");
        form.setName("user01");
        form.setTermsAgree(true);

        RequestToken form2 = new RequestToken();
        form2.setEmail("user01@gmail.com");
        form2.setPassword("_aA123456");
        String body = om.writeValueAsString(form2);

        String token = mockMvc.perform(post("/api/v1/member/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andReturn()
                .getResponse().getContentAsString(); // 응답 body 데이터를 반환

        //회원 전용, 관리자 전용
        mockMvc.perform(get("/api/v1/member/test1")
                .header("Authorization", "Bearer " + token))
                .andDo(print());
    }
}
