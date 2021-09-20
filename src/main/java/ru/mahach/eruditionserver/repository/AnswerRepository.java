package ru.mahach.eruditionserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mahach.eruditionserver.models.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
