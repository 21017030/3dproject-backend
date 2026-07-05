package dproject.route.controller;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import dproject.route.dto.NodeDto;
import dproject.route.dto.RequestDto;
import dproject.route.service.PathService;
import dproject.route.dto.FindDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j //로그를 찍을수 있게 어노테이션 추가
@RequiredArgsConstructor //final로 선언된 필드에 대한 생성자를 자동으로 생성해주는 Lombok 어노테이션(생성자 주입 방식으로 의존성 주입)
@RestController //RestController 구현
public class PathController {
    private final PathService pathService;
    /* 
    @GetMapping("/api/test/{endName}") //파라미터로 목적지 이름 받아서 경로 찾기 (/api/test/'목적지 이름')
    public List<Node> testRouteWithParam(@PathVariable("endName") String endName){
        List<Node> routes = pathService.findRoutes(endName);
        log.info(routes.toString()); //로그 찍기
        return routes; //목적지에 대한 경로 리턴
    }
    */
    // 목적지 이름만으로 하는 경로 탐색
    /*
    @PostMapping("/api/test/{endName}")
    public List<Node> testRouteWithDto(@PathVariable("endName") String endName, @RequestBody RequestDTO request){
        log.info("목적지: " + request.getDestination() + ", 엘레베이터 사용 여부: " + request.isUseElevator());
        List<Node> routes = pathService.findRoutes(endName, request.isUseElevator());
        log.info("경로: " + routes.toString()); //로그 찍기
        return routes;
    }
    */

    @GetMapping("/api/getNodes") //노드 전체 정보를 가져오는 api
    public ResponseEntity<List<NodeDto>> getNodes(){
        List<NodeDto> nodes = pathService.getAllNodes(); //서비스에서 노드 전체 정보를 가져오는 메서드 호출
        log.info("노드 정보: {}", 
        nodes.stream()
              .map(NodeDto::getName)
              .toList()
        ); //노드 정보에 포함된 노드들의 이름을 로그로 찍기

        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(nodes); //HTTP 상태 코드 200과 함께 노드 정보 반환
    }


    @PostMapping("/api/findPath")
    public ResponseEntity<List<NodeDto>> findPath(@RequestBody RequestDto request){
        log.info("시작점: {}, 목적지: {}, 엘리베이터 사용 여부: {}",
                request.getStartPoint(),
                request.getDestination(),
                request.isUseElevator());

        List<NodeDto> routes = pathService.findRoutes(
                request.getStartPoint(),
                request.getDestination(),
                request.isUseElevator()
        );
        log.info("경로: {}", 
        routes.stream()
              .map(NodeDto::getName)
              .toList()
        ); //경로에 포함된 노드들의 이름을 로그로 찍기


        return ResponseEntity.status(HttpStatus.OK).body(routes); //HTTP 상태 코드 200과 함께 경로 정보 반환
    }

    @PostMapping("/api/nodes/find") //노드 이름으로 노드 검색하는 api
    public ResponseEntity<NodeDto> findNodesByName(@RequestBody FindDto request){
        log.info("노드 이름으로 검색: {}", request.getNodeName());
        NodeDto node = pathService.findNodesByName(request.getNodeName());
        if (node != null) {
            log.info("검색 결과: {}", node.getName()); //검색 결과에 포함된 노드의 이름을 로그로 찍기
        } else {
            log.info("검색 결과: 노드를 찾을 수 없습니다."); //검색 결과가 없는 경우 로그로 찍기
            return ResponseEntity.notFound().build(); //HTTP 상태 코드 404 반환
        }
        return ResponseEntity.status(HttpStatus.OK).body(node); //HTTP 상태 코드 200과 함께 검색 결과 반환
    }

    /**
     * 키워드를 포함하는 노드 목록을 검색하는 API
     * (예: GET /api/search?keyword=로비)
     * 
     * @param keyword 검색할 키워드 (Query Parameter)
     * @return 검색된 노드 DTO 리스트 (검색 결과가 없거나 검색어가 비어 있으면 빈 리스트 반환)
     */
    @GetMapping("/api/search")
    public ResponseEntity<List<NodeDto>> searchNode(@RequestParam("keyword") String keyword) {
        log.info("키워드로 노드 검색 요청: '{}'", keyword);
        List<NodeDto> searchResults = pathService.searchNodes(keyword);
        log.info("검색된 노드 개수: {}개", searchResults.size());
        return ResponseEntity.ok(searchResults);
    }
}
