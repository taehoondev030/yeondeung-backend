package me.taehoon.yeondeung.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.taehoon.yeondeung.domain.Wish;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class AddWishRequest {

    private String content;

    public Wish toEntity() { // 생성자를 사용해 객체 생성
        return Wish.builder()
                .content(content)
                .build();
    }
}
