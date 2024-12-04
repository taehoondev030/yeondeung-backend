package me.taehoon.yeondeung.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 엔티티로 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wish {

    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "content", nullable = false) // 'content'라는 not null 컬럼과 매핑
    private  String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public Wish(String content, UserEntity user) {
        this.content = content;
        this.user = user;  // user를 builder로 설정할 수 있도록 추가
    }


    public void update(String content) {
        this.content = content;
    }


}
