package dproject.route.repository;

import dproject.route.domain.Node;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NodeRepository extends JpaRepository<Node, Long> {
    List<Node> findByName(String name); // 이름으로 노드 검색
    List<Node> findByNameContaining(String keyword); // 이름으로 노드 검색
}
