package com.example.oceanoconsciente.repository;

import com.example.oceanoconsciente.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query("SELECT SUM(q.pontuacao) FROM Quiz q WHERE q.usuario.id = :ID_USUARIO")
    Integer sumPointsByUsuarioId(@Param("ID_USUARIO") Long ID_USUARIO);
}
