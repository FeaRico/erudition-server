package ru.mahach.eruditionserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mahach.eruditionserver.models.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
