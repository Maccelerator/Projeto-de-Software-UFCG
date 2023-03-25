package com.ufcg.psoft.mercadofacil;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Cliente {

	private Long id;
	private Long cpf;
	private String nome;
	private Integer idade;
	private String endereco;

}