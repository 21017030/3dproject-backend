package dproject.route.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor //모든 필드를 매개변수로 받는 생성자 자동 생성
@NoArgsConstructor //매개변수가 없는 기본 생성자 자동 생성
@Entity
@Getter
@Setter
public class NodeType {
    @Id//기본 키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동으로 1씩 증가하는 ID 생성 전략
    @Column(name = "node_type_id")
    private Long nodeTypeId; //노드 타입의 고유 ID
    @Column(name = "is_elevator")
    private boolean isElevator; //엘리베이터인지 여부
    @Column(name = "is_stair")
    private boolean isStair; //계단인지 여부

}
