package springboot.ss11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.ss11.dto.DailyExportResponse;
import springboot.ss11.dto.TopSuppliesExportResponse;
import springboot.ss11.entity.StockTransaction;

import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Integer> {

    @Query("""
    SELECT new springboot.ss11.dto.DailyExportResponse(
        t.supply.id,
        t.supply.name,
        SUM(t.quantity)
    )
    FROM StockTransaction t
    WHERE t.type = springboot.ss11.entity.TransactionType.EXPORT
      AND FUNCTION('DATE', t.transactionDate) = CURRENT_DATE
    GROUP BY t.supply.id, t.supply.name
""")
    List<DailyExportResponse> getDailyExportStatistics();

    @Query("""
    SELECT new springboot.ss11.dto.TopSuppliesExportResponse(
        t.supply.name,
        SUM(t.quantity)
    )
    FROM StockTransaction t
    WHERE t.type = springboot.ss11.entity.TransactionType.EXPORT
    GROUP BY t.supply.id, t.supply.name
    ORDER BY SUM(t.quantity) DESC
    LIMIT 1
""")
    List<TopSuppliesExportResponse> findTopExport();


}
