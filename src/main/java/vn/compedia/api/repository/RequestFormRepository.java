package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.RequestForm;

@Repository
public interface RequestFormRepository extends JpaRepository<RequestForm, Long> {
}
