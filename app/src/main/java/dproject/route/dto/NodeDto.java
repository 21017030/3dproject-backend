package dproject.route.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import dproject.route.domain.Node;


//지금은 안쓰는 DTO 클래스로 본격적으로 API 만들때 쓸 예정
@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NodeDto {
    private String name;
    private double x;
    private double y;
    private String floor;
    private boolean isStair;
    private boolean isElevator;

    public static NodeDto CreateNodeDto(Node node) {
        return new NodeDto(
                node.getName(),
                node.getX(),
                node.getY(),
                node.getFloor(),
                node.getNodeType() != null && node.getNodeType().isStair(), //null 타입이 아닌 경우에만 계단 여부 확인
                node.getNodeType() != null && node.getNodeType().isElevator() //null 타입이 아닌 경우에만 엘리베이터 여부 확인
        );
    }
}