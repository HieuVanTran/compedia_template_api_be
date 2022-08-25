package vn.compedia.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.compedia.api.entity.PublishCompany;

import java.util.List;

public interface PublishCompanyRepository extends JpaRepository<PublishCompany, Long>, PublishCompanyRepositoryCustom {


    @Query("select p from PublishCompany p where p.publishName like :publishName")
    List<PublishCompany> findByName(@Param("publishName") String publishName);

    @Query("select p from PublishCompany p")
    List<PublishCompany> findAll();

}

