package vn.compedia.api.service;

import com.google.common.collect.Maps;
import com.google.maps.errors.ApiException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.repository.StoreRepository;
import vn.compedia.api.response.product.WarehouseResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class StoreService {
    @Value("${vn.compedia.static.context}")
    private String staticContext;
    @Value("${accept_image_file_types}")
    private String acceptImageTypes;


    @Autowired
    private StoreRepository storeRepository;


    public VietTienPageDto<?> warehouse(Map<String, Object> filters, int page, int pageSize, int languageId) throws VietTienInvalidParamsException, IOException, InterruptedException, ApiException {
        Map<String, String> errors = Maps.newHashMap();
        if (!errors.isEmpty()) {
            throw new VietTienInvalidParamsException(errors);
        }
        Map<String, String> sorts = new HashMap<>();
        if (filters.get("sorts") != null) {
            sorts.putAll((Map<String, String>) filters.get("sorts"));
        }
        Page<WarehouseResponse> showPage = storeRepository.searchWarehouse(filters, sorts, PageRequest.of(page - 1, pageSize), languageId);
        return VietTienPageDto.build(showPage);
    }

}
