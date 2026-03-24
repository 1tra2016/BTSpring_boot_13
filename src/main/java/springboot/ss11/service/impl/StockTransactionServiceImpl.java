package springboot.ss11.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.ss11.dto.DailyExportResponse;
import springboot.ss11.dto.TopSuppliesExportResponse;
import springboot.ss11.entity.StockTransaction;
import springboot.ss11.entity.Supplies;
import springboot.ss11.entity.TransactionType;
import springboot.ss11.repository.StockTransactionRepository;
import springboot.ss11.service.IStockTransactionService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StockTransactionServiceImpl implements IStockTransactionService {
    private final StockTransactionRepository stockTransactionRepository;

    @Override
    public StockTransaction exportSupplies(Supplies sup, int amount) {
        try {
            StockTransaction trans = new StockTransaction();
            trans.setSupply(sup);
            trans.setQuantity(amount);
            trans.setType(TransactionType.EXPORT);
            trans.setTransactionDate(LocalDateTime.now());

            StockTransaction saved = stockTransactionRepository.save(trans);

            log.info("Xuất kho thành công ID [{}], số lượng [-{}]", sup.getId(), amount);

            return saved;
        } catch (Exception e) {
            log.error("Thất bại khi xuất kho ID [{}]: Yêu cầu [{}]", sup.getId(), amount, e);
            throw e;
        }
    }

    @Override
    public StockTransaction importSupplies(Supplies sup, int amount) {
        try {
            StockTransaction trans = new StockTransaction();
            trans.setSupply(sup);
            trans.setQuantity(amount);
            trans.setType(TransactionType.IMPORT);
            trans.setTransactionDate(LocalDateTime.now());

            StockTransaction saved = stockTransactionRepository.save(trans);

            log.info("Nhập kho ID [{}], số lượng [+{}], tồn cũ [{}]",
                    sup.getId(),
                    amount,
                    sup.getQuantity()
            );

            return saved;
        } catch (Exception e) {
            log.error("Lỗi khi nhập kho ID [{}], số lượng [{}]", sup.getId(), amount, e);
            throw e;
        }
    }

    @Override
    public List<DailyExportResponse> getDailyExportStatistics() {
        long start = System.currentTimeMillis();
        log.info("Bắt đầu thống kê xuất kho trong ngày");

        List<DailyExportResponse> result = stockTransactionRepository.getDailyExportStatistics();

        long end = System.currentTimeMillis();
        log.info("Hoàn thành thống kê xuất kho trong ngày. Thời gian thực thi: {} ms", (end - start));

        return result;
    }

    @Override
    public List<TopSuppliesExportResponse> findTopExport() {
        long start = System.currentTimeMillis();
        log.info("Bắt đầu thống kê vật tư xuất kho nhiều nhất");

        List<TopSuppliesExportResponse> result = stockTransactionRepository.findTopExport();

        if (result == null || result.isEmpty()) {
            log.warn("Chưa có dữ liệu giao dịch để thống kê");
        }

        long end = System.currentTimeMillis();
        log.info("Hoàn thành thống kê top export. Thời gian: {} ms", (end - start));

        return result;
    }
}
