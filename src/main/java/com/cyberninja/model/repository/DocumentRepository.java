package com.cyberninja.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cyberninja.model.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>{

}
