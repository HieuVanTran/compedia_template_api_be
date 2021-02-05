package vn.compedia.api.util.user.specification;

import org.springframework.data.jpa.domain.Specification;
import vn.compedia.api.entity.Account;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification implements Specification<Account> {
    public static final String AGE = "age";

    private static final long serialVersionUID = -4415234963138321694L;

    private transient SearchCriteria criteria;

    public UserSpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (!criteria.getOperation().equalsIgnoreCase(":")) {
            return null;
        }
        if (criteria.getKey().equals(AGE)) {
            String ageValue = (String) criteria.getValue();
            return builder.equal(root.get(AGE), Integer.valueOf(ageValue));
        } else {
            return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
        }
    }
}
