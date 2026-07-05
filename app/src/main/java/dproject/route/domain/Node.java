package dproject.route.domain;

import lombok.Getter;
import lombok.Setter;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@AllArgsConstructor //모든 필드를 매개변수로 받는 생성자 자동 생성
@Getter //getName, getX, getY, getFloor, isStair(getIsStair 아님 주의) 같은 getter 메서드 자동 생성
@Setter //setName, setX, setY, setFloor, setStair(setIsStair 아님 주의) 같은 setter 메서드 자동 생성
@NoArgsConstructor //매개변수가 없는 기본 생성자 자동 생성
@Entity
//@ToString(of = {"name", "X", "Y", "floor", "isStair"})
public class Node {
    @Id //기본 키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동으로 1씩 증가하는 ID 생성 전략
    @Column(name = "node_id")
    private Long nodeId; // 노드의 고유 ID
    @Column (name = "node_name",nullable = false)
    private String name; //노드의 이름
    @Column (nullable = false)
    private double x; //노드의 x 좌표
    @Column (nullable = false)
    private double y; //노드의 y 좌표
    @Column(nullable = false)
    private String floor; //노드가 위치한 층
    @ManyToOne //다대일 관계 설정 (여러 노드가 하나의 노드 타입을 가질 수 있음)
    @JoinColumn(name = "node_type_id") //외래 키 컬럼 이름 지정
    private NodeType nodeType; //노드의 타입 (계단, 엘리베이터, 복도 등)

    /* 
    //@JsonIgnore //필요없는 필드는 JSON 변환에서 무시하도록 해주는 어노테이션(주석을 제거하면 됨)
    private String name; //노드의 이름
    // @JsonIgnore
    private double x; //노드의 x 좌표
    // @JsonIgnore
    private double y; //노드의 y 좌표
    // @JsonIgnore
    private String floor; //노드가 위치한 층
    // @JsonIgnore
    boolean isStair; //계단인지 여부
    // @JsonIgnore
    boolean isElevator; //엘리베이터인지 여부

    public boolean equals(Object o) { // 같은 노드인지 구분하기 위해 필요
    if (this == o) return true;
    if (!(o instanceof Node)) return false;
    Node n = (Node) o;
    return name.equals(n.name);
    }

    public int hashCode() { //같은 노드인지 구분하기 위해 필요
        return name.hashCode();
    }
    @Override
    public String toString() {
        return name; // ← 원하는 출력 형식
    }

    */
    
}
