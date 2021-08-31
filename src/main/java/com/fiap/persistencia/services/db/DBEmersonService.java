package com.fiap.persistencia.services.db;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.persistencia.domain.Cidade;
import com.fiap.persistencia.domain.Cliente;
import com.fiap.persistencia.domain.Endereco;
import com.fiap.persistencia.domain.Estado;
import com.fiap.persistencia.domain.Pais;
import com.fiap.persistencia.domain.enums.TipoCliente;
import com.fiap.persistencia.repositories.CidadeRepository;
import com.fiap.persistencia.repositories.ClienteRepository;
import com.fiap.persistencia.repositories.EnderecoRepository;
import com.fiap.persistencia.repositories.EstadoRepository;
import com.fiap.persistencia.repositories.PaisRepository;

@Service
public class DBEmersonService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	public void instantiateTestDatabase() throws ParseException {

		Pais pais1 = new Pais(null, "BR", "Brasil");

		Estado estado1 = new Estado(null, "AC", "Acre", pais1);
		Estado estado2 = new Estado(null, "AL", "Alagoas", pais1);
		Estado estado3 = new Estado(null, "AP", "Amapá", pais1);
		Estado estado4 = new Estado(null, "AM", "Amazonas", pais1);
		Estado estado5 = new Estado(null, "BA", "Bahia", pais1);
		Estado estado6 = new Estado(null, "CE", "Ceará", pais1);
		Estado estado7 = new Estado(null, "ES", "Espírito Santo", pais1);
		Estado estado8 = new Estado(null, "GO", "Goiás", pais1);
		Estado estado9 = new Estado(null, "MA", "Maranhão", pais1);
		Estado estado10 = new Estado(null, "MT", "Mato Grosso", pais1);
		Estado estado11 = new Estado(null, "MS", "Mato Grosso do Sul", pais1);
		Estado estado12 = new Estado(null, "MG", "Minas Gerais", pais1);
		Estado estado13 = new Estado(null, "PA", "Pará", pais1);
		Estado estado14 = new Estado(null, "PB", "Paraíba", pais1);
		Estado estado15 = new Estado(null, "PR", "Paraná", pais1);
		Estado estado16 = new Estado(null, "PE", "Pernambuco", pais1);
		Estado estado17 = new Estado(null, "PI", "Piauí", pais1);
		Estado estado18 = new Estado(null, "RJ", "Rio de Janeiro", pais1);
		Estado estado19 = new Estado(null, "RN", "Rio Grande do Norte", pais1);
		Estado estado20 = new Estado(null, "RS", "Rio Grande do Sul", pais1);
		Estado estado21 = new Estado(null, "RO", "Rondônia", pais1);
		Estado estado22 = new Estado(null, "RR", "Roraima", pais1);
		Estado estado23 = new Estado(null, "SC", "Santa Catarina", pais1);
		Estado estado24 = new Estado(null, "SP", "São Paulo", pais1);
		Estado estado25 = new Estado(null, "SE", "Sergipe", pais1);
		Estado estado26 = new Estado(null, "TO", "Tocantins", pais1);
		Estado estado27 = new Estado(null, "DF", "Distrito Federal", pais1);

		pais1.getEstados()
				.addAll(Arrays.asList(estado1, estado2, estado3, estado4, estado5, estado6, estado7, estado8, estado9,
						estado10, estado11, estado12, estado13, estado14, estado15, estado16, estado17, estado18,
						estado19, estado20, estado21, estado22, estado23, estado24, estado25, estado26, estado27));

		Cidade cidade1 = new Cidade(null, "São Paulo", estado24);
		Cidade cidade2 = new Cidade(null, "Ourinhos", estado24);
		Cidade cidade3 = new Cidade(null, "Uberlândia", estado12);

		estado24.getCidades().addAll(Arrays.asList(cidade1, cidade2));
		estado12.getCidades().addAll(Arrays.asList(cidade3));

		paisRepository.save(pais1);

		estadoRepository.saveAll(Arrays.asList(estado1, estado2, estado3, estado4, estado5, estado6, estado7, estado8,
				estado9, estado10, estado11, estado12, estado13, estado14, estado15, estado16, estado17, estado18,
				estado19, estado20, estado21, estado22, estado23, estado24, estado25, estado26, estado27));

		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

		Cliente c1 = new Cliente(null, "33235678911", "Teste Dias de Oliveira", "teste@email.com",
				TipoCliente.PESSOA_FISICA);
		Cliente c2 = new Cliente(null, "12345678901", "Emerson Dias de Oliveira", "emerson@email.com",
				TipoCliente.PESSOA_FISICA);

		Endereco end1 = new Endereco(null, "Rua Tiradentes", "300", "Apto 124", "Centro", "19920-123", true, c2,
				cidade1);
		Endereco end2 = new Endereco(null, "Rua Tiradentes", "100", null, "Centro", "19920-123", false, c2, cidade2);
		Endereco end3 = new Endereco(null, "Rua das Flores", "128", "Fundos", "Jardim das Flores", "65788-002", false,
				c1, cidade3);

		c1.getEnderecos().addAll(Arrays.asList(end3));
		c2.getEnderecos().addAll(Arrays.asList(end1, end2));

		clienteRepository.saveAll(Arrays.asList(c1, c2));
		enderecoRepository.saveAll(Arrays.asList(end1, end2, end3));
	}
}
