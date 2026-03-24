package springboot.ss11.service;

import org.springframework.data.domain.Page;
import springboot.ss11.dto.SuppliesCreateDTO;
import springboot.ss11.dto.SuppliesUpdateDTO;
import springboot.ss11.entity.Supplies;


public interface ISuppliesService {
    Supplies createSupplies(SuppliesCreateDTO req);
    Supplies updateSupplies(Long id, SuppliesUpdateDTO req);
    void deleteSupplies(Long id);
    Supplies getSupplies(Long id);
    Page<Supplies> getPageSupplies(int page, int size);
    Page<Supplies> findPageSupplies(String keyword, int page, int size);

    Supplies exportSupplies(Long id, int amount);
    Supplies importSupplies(Long id, int amount);
}
