package me.taehoon.yeondeung.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.taehoon.yeondeung.domain.Wish;
import me.taehoon.yeondeung.dto.AddWishRequest;
import me.taehoon.yeondeung.dto.UpdateWishRequest;
import me.taehoon.yeondeung.repository.LanternRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성
class LanternApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    LanternRepository lanternRepository;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        lanternRepository.deleteAll();
    }

    @DisplayName("addWish: 소망 추가에 성공한다.")
    @Test
    public void addWish() throws Exception {
        // given
        final String url = "/api/wishes";
        final String content = "content";
        final AddWishRequest userRequest = new AddWishRequest(content);

        // 객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        // 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Wish> wishes = lanternRepository.findAll();

        assertThat(wishes.size()).isEqualTo(1); // 크기가 1인지 검증
        assertThat(wishes.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllWishes: 소망 목록 조회에 성공한다.")
    @Test
    public void findAllWishes() throws Exception {
        // given
        final String url = "/api/wishes";
        final String content = "content";

        lanternRepository.save(Wish.builder()
                .content(content)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("findWish: 소망 조회에 성공한다.")
    @Test
    public void findWish() throws Exception {
        // given
        final String url = "/api/wishes/{id}";
        final String content = "content";

        Wish savedWish = lanternRepository.save(Wish.builder()
                .content(content)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, savedWish.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content));
    }

    @DisplayName("deleteWish: 소망 삭제에 성공한다.")
    @Test
    public void deleteWish() throws Exception {
        // given
        final String url = "/api/wishes/{id}";
        final String content = "content";

        Wish savedWish = lanternRepository.save(Wish.builder()
                .content(content)
                .build());

        // when
        mockMvc.perform(delete(url, savedWish.getId()))
                .andExpect(status().isOk());

        // then
        List<Wish> wishes = lanternRepository.findAll();

        assertThat(wishes).isEmpty();
    }

    @DisplayName("updateWish: 소망 수정에 성공한다.")
    @Test
    public void updateWish() throws Exception {
        // given
        final String url = "/api/wishes/{id}";
        final String content = "content";

        Wish savedWish = lanternRepository.save(Wish.builder()
                .content(content)
                .build());

        final String newContent = "new content";

        UpdateWishRequest request = new UpdateWishRequest(newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, savedWish.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Wish wish = lanternRepository.findById(savedWish.getId()).get();

        assertThat(wish.getContent()).isEqualTo(newContent);
    }
}