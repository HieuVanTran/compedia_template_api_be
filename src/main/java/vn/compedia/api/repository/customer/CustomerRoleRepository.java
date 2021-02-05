package vn.compedia.api.repository.customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.compedia.api.entity.CustomerRole;
import vn.compedia.api.util.DbConstant;

import java.util.List;

public interface CustomerRoleRepository extends CrudRepository<CustomerRole, Long> {
    @Query(" select ct from CustomerRole ct where ct.customerRoleId <> '" + DbConstant.ROLE_CONSUMERS + "' ")
    List<CustomerRole> getCustomerRole();

    @Query("select ct.discount from CustomerRole ct where ct.customerRoleId = :customerRoleId")
    Double findNameById(Long customerRoleId);
}
