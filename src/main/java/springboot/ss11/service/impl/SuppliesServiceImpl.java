package springboot.ss11.service.impl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.ss11.dto.SuppliesCreateDTO;
import springboot.ss11.dto.SuppliesUpdateDTO;
import springboot.ss11.entity.Supplies;
import springboot.ss11.exception.InsufficientStockException;
import springboot.ss11.exception.ResourceDeletedException;
import springboot.ss11.exception.ResourceNotFoundException;
import springboot.ss11.mapper.SuppliesMapper;
import springboot.ss11.repository.SuppliesRepository;
import springboot.ss11.service.IStockTransactionService;
import springboot.ss11.service.ISuppliesService;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SuppliesServiceImpl implements ISuppliesService {
    private final SuppliesRepository supRepo;
    private final SuppliesMapper supMapper;
    private final IStockTransactionService stockTransactionService;

    @Override
    public Supplies createSupplies(SuppliesCreateDTO req) {
        Supplies sup = supRepo.save(supMapper.createEntity(req));
        log.info("Đã tạo mới vật tư: [{}] với ID: [{}]", sup.getName(), sup.getId());
        return sup;
    }

    @Override
    public Supplies updateSupplies(Long id, SuppliesUpdateDTO req) {
        Supplies sup = getSupplies(id);
        log.info("Đã cập nhật vật tư ID [{}]", id);
        return  supRepo.save(supMapper.updateEntity(sup,req));
    }

    @Override
    public void deleteSupplies(Long id){
        Supplies sup = getSupplies(id);
        sup.setDeleted(true);
        supRepo.save(sup);
        log.info("Đã xóa mềm vật tư ID [{}]", id);
    }

    @Override
    public Supplies getSupplies(Long id){
        Supplies sup = supRepo.findById(id).orElseThrow(() -> {
            log.error("Không tìm thấy vật tư ID [{}]", id);
            return new ResourceNotFoundException("Không tìm thấy vật tư có id: " + id);
        });
        if(sup.isDeleted()) {
            log.warn("Truy cập vật tư đã bị xóa ID [{}]", id);
            throw new ResourceDeletedException("Vật tư có id: "+id+" đã bị xóa");
        }
        return sup;
    }

    @Override
    public Page<Supplies> getPageSupplies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return supRepo.findAllByIsDeletedFalse(pageable);
    }

    @Override
    public Page<Supplies> findPageSupplies(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Supplies> result = supRepo.searchByKeyword(keyword, pageable);

        if (result.isEmpty()) log.info("Không tìm thấy vật tư nào khớp với từ khóa: [{}]", keyword);
        else log.debug("Tìm kiếm vật tư với keyword [{}], số kết quả: {}", keyword, result.getTotalElements());

        return result;
    }

    @Override
    public Supplies exportSupplies(Long id, int amount){
        Supplies sup = getSupplies(id);
        int quantity = sup.getQuantity();
        if(quantity < amount) {
            log.error("Thất bại khi xuất kho ID [{}]: Yêu cầu [{}], hiện có [{}]", id, amount, quantity);
            throw new InsufficientStockException("Vật tư có id: "+ id +" không đủ số lượng xuất kho");
        }
        sup.setQuantity(quantity - amount);

        supRepo.save(sup);
        stockTransactionService.exportSupplies(sup, amount);
        log.info("Xuất kho vật tư ID [{}], số lượng [-{}], tồn còn [{}]", id, amount, sup.getQuantity());
        return sup;
    }

    @Override
    public Supplies importSupplies(Long id, int amount){
        Supplies sup = getSupplies(id);
        sup.setQuantity(sup.getQuantity()+amount);

        supRepo.save(sup);
        stockTransactionService.importSupplies(sup, amount);
        log.info("Nhập kho vật tư ID [{}], số lượng [+{}], tồn mới [{}]", id, amount, sup.getQuantity());
        return sup;
    }



}
