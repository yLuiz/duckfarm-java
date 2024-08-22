package com.example.duckfarm.db.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.duckfarm.db.model.Duck;

@Repository
public interface DuckRepository extends JpaRepository<Duck, Long> {

    @Query("SELECT d FROM Duck d")
    public @NonNull
    Page<Duck> findAllPage(@NonNull Pageable page);
}
