package ru.mahach.eruditionserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mahach.eruditionserver.models.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
