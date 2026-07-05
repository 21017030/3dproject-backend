package dproject.route.repository;

import dproject.route.domain.NodeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeTypeRepository extends JpaRepository<NodeType, Long>{
    
}
