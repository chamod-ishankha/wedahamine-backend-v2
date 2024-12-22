package org.bytecub.WedahamineBackend.utils.Search;

import jakarta.persistence.criteria.Predicate;
import org.hibernate.sql.ast.tree.predicate.LikePredicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterUtility<T, ID extends Serializable> {

    private final JpaSpecificationExecutor<T> specificationExecutor;

    public FilterUtility(JpaRepository<T, ID> repository) {
        this.specificationExecutor = (JpaSpecificationExecutor<T>) repository;
    }

    public Page<T> filterRecords(
            int page,
            int perPage,
            String sort,
            String direction,
            List<FilterCriteria<T>> filterCriteriaList) {

        Sort sortThis = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, perPage, sortThis);

        Specification<T> spec = (root, query, criteriaBuilder) -> {

            // collect specifications
            List<Specification<T>> specifications = filterCriteriaList.stream()
                    .map(criteria -> criteria.buildPredicate(root, criteriaBuilder))
                    .collect(Collectors.toList());

            // Convert Specifications to Predicates
            Predicate[] predicates = specifications.stream()
                    .map(specification -> specification.toPredicate(root, query, criteriaBuilder))
                    .toArray(Predicate[]::new);

            List<Predicate> orPredicates = new ArrayList<>();
            List<Predicate> andPredicates = new ArrayList<>();
            for (Predicate predicate : predicates) {
                if (predicate instanceof LikePredicate) {
                    orPredicates.add(predicate);
                } else {
                    andPredicates.add(predicate);
                }
            }

            Predicate finalPredicate;

            if (!orPredicates.isEmpty()) {
                finalPredicate = criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));
            } else {
                finalPredicate = criteriaBuilder.conjunction(); // Empty AND predicate
            }

            if (!andPredicates.isEmpty()) {
                finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.and(andPredicates.toArray(new Predicate[0])));
            }

            return finalPredicate;
        };

        return specificationExecutor.findAll(spec, pageable);
    }


    public List<T> filterAll(List<FilterCriteria<T>> filterCriteriaList, String sort, String direction) {
        Sort sortThis = Sort.by(Sort.Direction.fromString(direction), sort);

        Specification<T> spec = buildSpecification(filterCriteriaList);

        // Use the specificationExecutor to fetch all matching entities without pagination
        return specificationExecutor.findAll(spec, sortThis);
    }

    private Specification<T> buildSpecification(List<FilterCriteria<T>> filterCriteriaList) {
        return (root, query, criteriaBuilder) -> {

            // Collect specifications
            List<Specification<T>> specifications = filterCriteriaList.stream()
                    .map(criteria -> criteria.buildPredicate(root, criteriaBuilder))
                    .collect(Collectors.toList());

            // Convert Specifications to Predicates
            Predicate[] predicates = specifications.stream()
                    .map(specification -> specification.toPredicate(root, query, criteriaBuilder))
                    .toArray(Predicate[]::new);

            List<Predicate> orPredicates = new ArrayList<>();
            List<Predicate> andPredicates = new ArrayList<>();
            for (Predicate predicate : predicates) {
                if (predicate instanceof LikePredicate) {
                    orPredicates.add(predicate);
                } else {
                    andPredicates.add(predicate);
                }
            }

            Predicate finalPredicate;

            if (!orPredicates.isEmpty()) {
                finalPredicate = criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));
            } else {
                finalPredicate = criteriaBuilder.conjunction(); // Empty AND predicate
            }

            if (!andPredicates.isEmpty()) {
                finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.and(andPredicates.toArray(new Predicate[0])));
            }

            return finalPredicate;
        };
    }
}

