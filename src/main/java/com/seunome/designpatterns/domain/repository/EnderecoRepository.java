package com.seunome.designpatterns.domain.repository;

import com.seunome.designpatterns.domain.model.Endereco;
import org.springframework.data.repository.CrudRepository;

public interface EnderecoRepository extends CrudRepository<Endereco, String> {
}
