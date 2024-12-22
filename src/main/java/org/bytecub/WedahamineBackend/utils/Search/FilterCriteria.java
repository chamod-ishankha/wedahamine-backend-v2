package org.bytecub.WedahamineBackend.utils.Search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public interface FilterCriteria<T> {
    Specification<T> buildPredicate(Root<T> root, CriteriaBuilder criteriaBuilder);
}