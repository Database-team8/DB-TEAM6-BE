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

import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Board> findAllByDynamicFilter(
            Category category,
            BoardStatus status,
            Long itemTypeId,
            Long locationId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
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

        return em.createQuery(cq).getResultList();
    }
}