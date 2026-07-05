package dproject.route.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private String startPoint; //출발지 이름
    private String destination; //목적지 이름
    private boolean useElevator;//엘리베이터 사용 여부
}
