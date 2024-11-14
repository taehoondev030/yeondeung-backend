package me.taehoon.yeondeung.dto;

import lombok.Getter;
import me.taehoon.yeondeung.domain.Wish;

@Getter
public class WishResponse {

    private final String content;

    // 생성자
    public WishResponse(Wish wish) {
        this.content = wish.getContent();
    }
}
