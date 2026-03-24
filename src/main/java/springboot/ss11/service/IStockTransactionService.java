package springboot.ss11.service;

import springboot.ss11.dto.DailyExportResponse;
import springboot.ss11.dto.TopSuppliesExportResponse;
import springboot.ss11.entity.StockTransaction;
import springboot.ss11.entity.Supplies;

import java.util.List;

public interface IStockTransactionService {
    StockTransaction exportSupplies(Supplies sup, int amount);
    StockTransaction importSupplies(Supplies sup, int amount);
    List<DailyExportResponse> getDailyExportStatistics();
    List<TopSuppliesExportResponse> findTopExport();
}
