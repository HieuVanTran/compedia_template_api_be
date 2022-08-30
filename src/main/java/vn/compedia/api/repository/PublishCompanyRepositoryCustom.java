package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.book.PublishCompanyResponse;

import java.math.BigInteger;
import java.util.List;

public interface PublishCompanyRepositoryCustom {
    List<PublishCompanyResponse> getAllPublishCompany();

    Page<PublishCompanyResponse> search(String publishName, String email, String agentPeople,
                                        String sortField, String sortOrder, Integer page, Integer size, Pageable pageable);

    BigInteger getTotalPublisher();
}
