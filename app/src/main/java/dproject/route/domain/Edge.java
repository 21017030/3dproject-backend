package dproject.route.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@AllArgsConstructor //모든 필드를 매개변수로 받는 생성자 자동 생성
@Getter //getStart, getEnd, getWeight 같은 getter 메서드 자동 생성
@Setter //setStart, setEnd, setWeight 같은 setter 메서드 자동 생성
@NoArgsConstructor //매개변수가 없는 기본 생성자 자동 생성
@Entity
public class Edge { //간선 클래스
    @Id //기본 키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동으로 1씩 증가하는 ID 생성 전략
    @Column(name = "edge_id")
    private Long edgeId; //간선의 고유 ID
    @ManyToOne //다대일 관계 설정 (여러 간선이 하나의 시작 노드를 가질 수 있음)
    @JoinColumn(name = "start_node_id") //외래 키 컬럼 이름 지정
    private Node start; //간선의 시작 노드
    @ManyToOne //다대일 관계 설정 (여러 간선이 하나의 끝 노드를 가질 수 있음)
    @JoinColumn(name = "end_node_id") //외래 키 컬럼 이름 지정
    private Node end; //간선의 끝 노드
    @Column(name = "weight", nullable = false)
    private double weight; //간선의 가중치
}
