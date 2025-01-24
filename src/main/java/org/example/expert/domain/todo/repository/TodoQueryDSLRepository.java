package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TodoQueryDSLRepository {

    private final JPAQueryFactory queryFactory;

    public TodoQueryDSLRepository(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Optional<Todo> findByIdWithUser(Long todoId) {
        QTodo todo = QTodo.todo;
        QUser user = QUser.user;

        Todo result = queryFactory
                .select(todo)
                .leftJoin(todo.user, user)
                .fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();
        return Optional.ofNullable(result);
    }
}
