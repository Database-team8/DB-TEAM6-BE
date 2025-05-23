package com.ajoufinder.be.board.repository;

import com.ajoufinder.be.board.domain.Board;
import com.ajoufinder.be.board.domain.constant.BoardStatus;
import com.ajoufinder.be.board.domain.constant.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final EntityManager em;

    @Override
    public Page<Board> findAllByDynamicFilter(
            Category category,
            BoardStatus status,
            Long itemTypeId,
            Long locationId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // 메인 쿼리
        CriteriaQuery<Board> cq = cb.createQuery(Board.class);
        Root<Board> board = cq.from(Board.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(board.get("category"), category));
        predicates.add(cb.notEqual(board.get("status"), BoardStatus.DELETED));

        if (status != null) {
            predicates.add(cb.equal(board.get("status"), status));
        }
        if (itemTypeId != null) {
            predicates.add(cb.equal(board.get("itemType").get("id"), itemTypeId));
        }
        if (locationId != null) {
            predicates.add(cb.equal(board.get("location").get("id"), locationId));
        }
        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(board.get("relatedDate"), startDate.atStartOfDay()));
        }
        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(board.get("relatedDate"), endDate.atTime(23, 59, 59)));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(board.get("createdAt")));

        List<Board> content = em.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // 카운트 쿼리
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Board> countRoot = countQuery.from(Board.class);
        countQuery.select(cb.count(countRoot));
        List<Predicate> countPredicates = new ArrayList<>();
        countPredicates.add(cb.equal(countRoot.get("category"), category));
        countPredicates.add(cb.notEqual(countRoot.get("status"), BoardStatus.DELETED));

        if (status != null) {
            countPredicates.add(cb.equal(countRoot.get("status"), status));
        }
        if (itemTypeId != null) {
            countPredicates.add(cb.equal(countRoot.get("itemType").get("id"), itemTypeId));
        }
        if (locationId != null) {
            countPredicates.add(cb.equal(countRoot.get("location").get("id"), locationId));
        }
        if (startDate != null) {
            countPredicates.add(cb.greaterThanOrEqualTo(countRoot.get("relatedDate"), startDate.atStartOfDay()));
        }
        if (endDate != null) {
            countPredicates.add(cb.lessThanOrEqualTo(countRoot.get("relatedDate"), endDate.atTime(23, 59, 59)));
        }

        countQuery.where(countPredicates.toArray(new Predicate[0]));

        Long total = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }

}