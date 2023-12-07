package com.ssamz.demo.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data    // Lombok 라이브러리의 어노테이션, 클래스 내 모든 필드에 대한 getter, setter 자동 생성
@AllArgsConstructor    // 모든 필드의 값을 파라미터로 받는 생성자를 만듬
@NoArgsConstructor    // 파라미터가 없는 기본 생성자 만듬
@Builder    // 빌더 패턴 클래스를 생성하여 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
@Entity    // JPA에서 사용하는 어노테이션, 데이터베이스의 테이블과 매핑한다는 뜻.
public class Post {
    @Id    // 해당 필드가 데이터베이스 테이블의 기본 키 역할을 한다는 뜻
    @GeneratedValue(strategy = GenerationType.IDENTITY)	// 기본키 생성을 데이터베이스에 위임. 기본키를 DB에서 자동으로 생성
    private int id;

    @Column(nullable = false, length = 100)	// 해당 필드가 데이터베이스 테이블의 컬럼이며, null값을 허용하지 않고 최대길이 100
    private String title;

    // 서머노트를 적용하여 다양한 html 태그 포함
    @Lob	// 데이터베이스의 LOB(Large Object) 타입으로 매핑하는 것이며 큰 사이즈의 데이터 저장 가능
    @Column(nullable = false)	// not null 특성의 컬럼 선언
    private String content;

    @CreationTimestamp    // 데이터베이스에 엔티티를 저장할 때 현재 시각을 자동으로 저장.
    private Timestamp createDate;

    private int cnt;

    @ManyToOne(fetch = FetchType.EAGER)	// Post와 User간의 N:1 관계를 나타내며, EAGER를 통해 Post 조회 시 연관된 User도 함께 조회
    @JoinColumn(name = "userid")	// User와 연결된 외래키의 이름을 userid로 설정한다는 것을 나타냄.
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE)
    @OrderBy("createDate asc")
    private List<Reply> replyList;

}
