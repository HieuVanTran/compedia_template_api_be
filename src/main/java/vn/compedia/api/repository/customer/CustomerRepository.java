package vn.compedia.api.repository.customer;

import org.springframework.data.repository.CrudRepository;
import vn.compedia.api.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>, CustomerRepositoryCustom {
    Customer findCustomerByCustomerId(Long id);
}
