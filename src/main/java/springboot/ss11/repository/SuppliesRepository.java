package springboot.ss11.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.ss11.entity.Supplies;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuppliesRepository extends JpaRepository<Supplies, Long> {

    Page<Supplies> findAllByIsDeletedFalse(Pageable pageable);

    @Query("SELECT s FROM Supplies s " +
            "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND s.isDeleted = false")
    Page<Supplies> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
