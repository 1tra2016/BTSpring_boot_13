package springboot.ss11.mapper;

import org.springframework.stereotype.Component;
import springboot.ss11.dto.SuppliesCreateDTO;
import springboot.ss11.dto.SuppliesResponseDTO;
import springboot.ss11.dto.SuppliesUpdateDTO;
import springboot.ss11.entity.Supplies;

@Component
public class SuppliesMapper {

    public Supplies createEntity(SuppliesCreateDTO req){
        Supplies supplies = new Supplies();
        supplies.setName(req.getName());
        supplies.setSpecification(req.getSpecification());
        supplies.setProvider(req.getProvider());
        supplies.setUnit(req.getUnit());
        supplies.setQuantity(0);
        supplies.setDeleted(false);
        return supplies;
    }

    public Supplies updateEntity(Supplies oldSup, SuppliesUpdateDTO newSup){
        oldSup.setName(newSup.getName());
        oldSup.setSpecification(newSup.getSpecification());
        oldSup.setProvider(newSup.getProvider());
        return oldSup;
    }

    public SuppliesResponseDTO toResponseDTO(Supplies supplies){
        SuppliesResponseDTO suppliesResponseDTO = new SuppliesResponseDTO();
        suppliesResponseDTO.setId(supplies.getId());
        suppliesResponseDTO.setName(supplies.getName());
        suppliesResponseDTO.setSpecification(supplies.getSpecification());
        suppliesResponseDTO.setProvider(supplies.getProvider());
        suppliesResponseDTO.setUnit(supplies.getUnit());
        suppliesResponseDTO.setQuantity(supplies.getQuantity());
        return suppliesResponseDTO;
    }
}