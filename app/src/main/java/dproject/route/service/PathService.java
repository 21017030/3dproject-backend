package dproject.route.service;


import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.stereotype.Service;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import java.util.List;
import dproject.route.repository.EdgeRepository;
import dproject.route.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import dproject.route.domain.Node;
import dproject.route.domain.Edge;
import dproject.route.dto.NodeDto;

@Service
@RequiredArgsConstructor // final로 선언된 필드에 대한 생성자를 자동으로 생성해주는 Lombok 어노테이션(생성자 주입 방식으로 의존성 주입)
public class PathService {
    private final NodeRepository nodeRepository;
    private final EdgeRepository edgeRepository;

    // 최단 경로를 찾는 메서드
    public List<NodeDto> findRoutes(String startName, String endName, boolean useElevator){
        Graph<Node, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class); //방향이 없는 가중치 그래프 생
        List<Node> nodes = nodeRepository.findAll(); //노드 전체를 가져옴(레파지토리에서 자동으로 만들어주는 함수)
        List<Edge> edges = edgeRepository.findAll(); //엣지 전체를 가져옴
        
        Node startNode = null;
        Node endNode = null;
    
        List<Node> startList = nodeRepository.findByName(startName); //시작점 이름으로 노드 검색(리턴값이  List)
        List<Node> endList = nodeRepository.findByName(endName); //목적지 이름으로 노드 검색
        if(startList.isEmpty() || endList.isEmpty()) {
            throw new IllegalArgumentException("시작점 또는 목적지 이름이 잘못되었습니다."); //시작점이나 목적지 이름이 잘못된 경우 예외 처리
        }
        startNode = startList.get(0); //시작점 노드 가져오기
        endNode = endList.get(0); //목적지 노드 가져오기
        for (Node node : nodes){
            graph.addVertex(node); //그래프에 노드 추가
        }

        for (Edge edge : edges){
            Node start = edge.getStart();
            Node end = edge.getEnd();
            DefaultWeightedEdge graphEdge = graph.addEdge(start, end);
            if(graphEdge != null){ //간선이 성공적으로 추가된 경우에만 가중치 설정(null로 반환되는 경우는 이미 간선이 존재하기 떄문에 생김)
                double weight = edge.getWeight();
                if(useElevator == false){
                    if(!(start.getFloor().equals(end.getFloor())) //층이 다르고(다른 층 이동)
                && (start.getNodeType().isElevator() == true && end.getNodeType().isElevator() == true)){ //양쪽 다 엘리베이터인 경우
                    weight = 1000000.0; //가중치를 아주 크게 설정하기
                } 
                }else{ //엘리베이터를 사용하는 경우
                    if(!(start.getFloor().equals(end.getFloor())) //층이 다르고(다른 층 이동)
                    && (start.getNodeType().isStair() == true && end.getNodeType().isStair() == true)){ //양쪽 다 계단인 경우
                        weight = 1000000.0; //가중치를 아주 크게 설정하기
                    }
                }
                graph.setEdgeWeight(graphEdge, weight); //간선의 가중치 설정
            }
        }
        
        DijkstraShortestPath<Node, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph); //다익스트라 알고리즘 객체 생성<노드 타입, 간선 타입>

        var path = dijkstra.getPath(startNode, endNode); //시작점 노드에서 목적지 노드까지의 최단 경로 계산
        if (path == null) {
            throw new IllegalArgumentException("시작점과 목적지 사이에 경로가 존재하지 않습니다."); //경로가 존재하지 않는 경우 예외 처리
        }
        
        return path.getVertexList()//최단 경로에 포함된 노드들의 리스트 반환
        .stream()
        .map(Node -> NodeDto.CreateNodeDto(Node)) //Node 엔티티를 NodeDto로 변환
        .toList();
    }

    // 모든 노드를 가져오는 메서드
    public List<NodeDto> getAllNodes() {
        return nodeRepository.findAll() //노드 전체를 가져옴
        .stream()
        .map(Node -> NodeDto.CreateNodeDto(Node)) //Node 엔티티를 NodeDto로 변환
        .toList();
    }

    // 노드 이름으로 노드를 검색하는 메서드
    public NodeDto findNodesByName(String name){
        return nodeRepository.findByName(name) //노드 이름으로 노드 검색
        .stream()
        .map(Node -> NodeDto.CreateNodeDto(Node)) //Node 엔티티를 NodeDto로 변환
        .findFirst()
        .orElse(null) ; //검색 결과가 없는 경우 null 반환
    }

    /**
     * 이름에 특정 키워드가 포함된 노드 목록을 검색하는 메서드
     * 
     * @param keyword 검색어 키워드
     * @return 키워드가 포함된 노드들의 DTO 리스트
     */
    public List<NodeDto> searchNodes(String keyword) {
        // 1. 방어적 코드: 검색 키워드가 null이거나 좌우 공백을 제거했을 때 빈 문자열인 경우
        //    불필요하게 DB 전체 조회를 유발하는 LIKE '%%' 조회를 차단하기 위해 바로 빈 리스트 반환
        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }
        
        // 2. DB에서 키워드가 포함된 노드 목록 조회 (SQL: LIKE %keyword%)
        return nodeRepository.findByNameContaining(keyword)
        .stream()
        // 3. Entity 객체(Node)를 외부 노출용 데이터 전송 객체(NodeDto)로 매핑
        .map(Node -> NodeDto.CreateNodeDto(Node))
        .toList();
    }
}