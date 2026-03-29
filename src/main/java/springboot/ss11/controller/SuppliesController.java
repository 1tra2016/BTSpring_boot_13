package springboot.ss11.controller;

import jakarta.validation.Valid;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.ss11.dto.*;
import springboot.ss11.entity.Supplies;
import springboot.ss11.mapper.SuppliesMapper;
import springboot.ss11.service.IStockTransactionService;
import springboot.ss11.service.ISuppliesService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/v1/employees"})
@RequiredArgsConstructor
public class SuppliesController {
    private final ISuppliesService supService;
    private final IStockTransactionService stockTransactionService;
    private final SuppliesMapper supMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<SuppliesResponseDTO>> createSupplies(
            @Valid @RequestBody SuppliesCreateDTO req
    ){
        return ResponseEntity.ok(ApiResponse.success(201, supMapper.toResponseDTO(
                supService.createSupplies(req)
        )));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SuppliesResponseDTO>> updateSupplies(
            @PathVariable Long id,
            @Valid @RequestBody SuppliesUpdateDTO req
    ){
        return ResponseEntity.ok(ApiResponse.success(200, supMapper.toResponseDTO(
                supService.updateSupplies(id, req)
        )));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteSupplies(
            @PathVariable Long id
    ){
        supService.deleteSupplies(id);
        return ResponseEntity.ok(ApiResponse.success(200, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SuppliesResponseDTO>>> getList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Supplies> result = supService.getPageSupplies(page, size);

        List<SuppliesResponseDTO> data = result
                .stream().map(sup -> supMapper.toResponseDTO(sup))
                .toList();

        return ResponseEntity.ok(ApiResponse.success(
                200,
                data,
                Map.of(
                        "page", result.getNumber(),
                        "size", result.getSize(),
                        "total", result.getTotalElements(),
                        "totalPages", result.getTotalPages()
                )
        ));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SuppliesResponseDTO>>> getList(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Supplies> result = supService.findPageSupplies(name, page, size);

        List<SuppliesResponseDTO> data = result
                .stream().map(sup -> supMapper.toResponseDTO(sup))
                .toList();

        return ResponseEntity.ok(ApiResponse.success(
                200,
                data,
                Map.of(
                        "page", result.getNumber(),
                        "size", result.getSize(),
                        "total", result.getTotalElements(),
                        "totalPages", result.getTotalPages()
                )
        ));
    }

    @PatchMapping("/{id}/export")
    public ResponseEntity<ApiResponse<SuppliesResponseDTO>> exportSupplies(
            @PathVariable Long id,
            @RequestParam int amount
    ){
        return  ResponseEntity.ok(ApiResponse.success(200, supMapper.toResponseDTO(
                supService.exportSupplies(id, amount)
        )));
    }

    @PatchMapping("/{id}/import")
    public ResponseEntity<ApiResponse<SuppliesResponseDTO>> importSupplies(
            @PathVariable Long id,
            @RequestParam int amount
    ){
        return  ResponseEntity.ok(ApiResponse.success(200, supMapper.toResponseDTO(
                supService.importSupplies(id, amount)
        )));
    }

    @GetMapping("/statistics/daily-export")
    public ResponseEntity<ApiResponse<List<DailyExportResponse>>>  getDailyExportStatistics(){
        return  ResponseEntity.ok(ApiResponse.success(200,
                stockTransactionService.getDailyExportStatistics()
        ));
    }

    @GetMapping("/statistics/top-export")
    public ResponseEntity<ApiResponse<List<TopSuppliesExportResponse>>>   findTopExport(){
        return  ResponseEntity.ok(ApiResponse.success(200,
                stockTransactionService.findTopExport()
        ));
    }


}
