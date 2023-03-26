package com.ufcg.psoft.mercadofacil;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VolatilLoteRepositoryTest {

   @Autowired
   LoteRepository<Lote, Long> driver;

   Lote lote;
   Lote resultado;
   Produto produto;
   
   Produto manga;
   Lote miguel;
   Lote mateus;
   Lote sergio;
   Lote evaldo;
   Lote gabriel;
   Lote emile;
   Lote[] autoajuda;


   @BeforeEach
   void setup() {
       produto = Produto.builder()
               .id(1L)
               .nome("Produto Base")
               .codigoBarra("123456789")
               .fabricante("Fabricante Base")
               .preco(125.36)
               .build();
       lote = Lote.builder()
               .id(1L)
               .numeroDeItens(100)
               .produto(produto)
               .build();
       
       // Atributos criados pelo aluno:
       manga = Produto.builder()
    		   .id(2L)
    		   .nome("Mangás")
    		   .codigoBarra("987654321")
    		   .fabricante("Mangakás")
    		   .preco(19.99)
    		   .build();
       
       miguel = Lote.builder()
               .id(7L)
               .numeroDeItens(105)
               .produto(manga)
               .build();
       
       mateus = Lote.builder()
               .id(2L)
               .numeroDeItens(110)
               .produto(manga)
               .build();
       
       sergio = Lote.builder()
               .id(3L)
               .numeroDeItens(120)
               .produto(manga)
               .build();
       
       evaldo = Lote.builder()
               .id(4L)
               .numeroDeItens(130)
               .produto(manga)
               .build();
       
       gabriel = Lote.builder()
               .id(5L)
               .numeroDeItens(140)
               .produto(manga)
               .build();
       
       emile = Lote.builder()
               .id(6L)
               .numeroDeItens(150)
               .produto(manga)
               .build();
       
       //-----------------------------
   }

   @AfterEach
   void tearDown() {
       produto = null;
       driver.deleteAll();
   }

   @Test
   @DisplayName("Adicionar o primeiro Lote no repositorio de dados")
   void salvarPrimeiroLote() {
       resultado = driver.save(lote);

       assertEquals(driver.findAll().size(),1);
       assertEquals(resultado.getId().longValue(), lote.getId().longValue());
       assertEquals(resultado.getProduto(), produto);
   }

   @Test
   @DisplayName("Adicionar o segundo Lote (ou posterior) no repositorio de dados")
   void salvarSegundoLoteOuPosterior() {
       Produto produtoExtra = Produto.builder()
               .id(2L)
               .nome("Produto Extra")
               .codigoBarra("987654321")
               .fabricante("Fabricante Extra")
               .preco(125.36)
               .build();
       Lote loteExtra = Lote.builder()
               .id(2L)
               .numeroDeItens(100)
               .produto(produtoExtra)
               .build();
       driver.save(lote);

       resultado = driver.save(loteExtra);

       assertEquals(driver.findAll().size(),2);
       assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
       assertEquals(resultado.getProduto(), produtoExtra);

   }
   
   @Test
   @DisplayName("Encontrar 1 lote de produtos no repositório")
   void encontraUmLote() {
	   try {
		   resultado = driver.find(2L);
		   fail("IndexOutOfBoundsException");
	   } catch (IndexOutOfBoundsException e) {
		   assertTrue(true);
	   }
	   assertEquals(driver.findAll().size(), 0);
	   resultado = driver.save(miguel);
	   
	   assertEquals(driver.findAll().size(), 1);
       assertEquals(resultado.getId().longValue(), driver.find(7L).getId().longValue());
       assertEquals(resultado.getProduto(), manga);   
   }
   
   @Test
   @DisplayName("Encontrar 2 lotes de produtos no repositório")
   void encontraDoisLotes() {
	   try {
		   resultado = driver.find(2L);
		   fail("IndexOutOfBoundsException");
	   } catch (IndexOutOfBoundsException e) {
		   assertTrue(true);
	   }
	   
	   assertEquals(driver.findAll().size(), 0);
	   Lote[] result = {driver.save(sergio), driver.save(evaldo)};
	   
	   assertEquals(driver.findAll().size(), 2);
	   assertEquals(result[0].getId().longValue(), driver.find(3L).getId().longValue());
	   assertEquals(result[1].getId().longValue(), driver.find(4L).getId().longValue());
       assertEquals(result[0].getProduto(), manga);
       assertEquals(result[1].getProduto(), manga);
       assertEquals(result[0], result[1]);
       
	   
   }
   
   @Test
   @DisplayName("Encontra todos os lotes de produtos no repositório")
   void encontraTodosLotes() {
	   try {
		   resultado = driver.find(2L);
		   fail("IndexOutOfBoundsException");
	   } catch (IndexOutOfBoundsException e) {
		   assertTrue(true);
	   }
	   
	   Lote[] lotes = {lote, miguel, mateus, sergio, evaldo, gabriel, emile};
	   List<Lote> result = new ArrayList<>();
	   long[] ids = new long[7];
	   int[] numItens = new int[7];
	   String[] produtos = new String[7];
	   
	   assertEquals(driver.findAll().size(), 0);
	   
	   for (int i = 0; i < 7; i++) {
		   result.add(driver.save(lotes[i]));
		   ids[i] = lotes[i].getId();
		   numItens[i] = lotes[i].getNumeroDeItens();
		   produtos[i] = lotes[i].getProduto().getNome();
	   }
	   
	   boolean igual = true;
	   
	   for (int ii = 0; ii < 7; ii ++) {
		   if (driver.findAll().get(ii).getId().equals(result.get(ii).getId())) {
			   igual = false;
		   }
		   if (driver.findAll().get(ii).getNumeroDeItens() == (result.get(ii).getNumeroDeItens())) {
			   igual = false;
		   }
		   if (driver.findAll().get(ii).getProduto().getNome().equals(result.get(ii).getProduto().getNome())) {
			   igual = false;
		   }
	   }
	   
	   assertEquals(driver.findAll().size(), 7);
	   assertEquals(true, igual);
	   
	   
   }
   
   @Test
   @DisplayName("Atualiza 1 lote de produtos no repositório")
   void atualizaUmLote() {
	  assertEquals(driver.findAll().size(), 0);
	  Lote[] result = {driver.save(miguel), driver.save(mateus)};
	  assertEquals(driver.findAll().size(), 2);
	  assertEquals(driver.find(miguel.getId()), result[0].getId());
	  assertEquals(driver.find(mateus.getId()), result[1].getId());
	  
	  resultado = driver.update(sergio);
	  assertEquals(driver.findAll().size(), 1);
	  assertEquals(sergio.getId(), resultado.getId());
	  
	  Lote resultado = driver.update(evaldo);
	  assertEquals(driver.findAll().size(), 1);
	  assertEquals(evaldo.getId(), resultado.getId());
   }
   
   
   @Test
   @DisplayName("Deleta 1 lote de produtos no repositório")
   void deletaUmLote() {
	   Lote[] lotes = {lote, miguel, mateus, sergio, evaldo, gabriel, emile};
	   List<Lote> result = new ArrayList<>();
	   
	   assertEquals(driver.findAll().size(), 0);
	   
	   for (int i = 0; i < 7; i++) {
		   result.add(driver.save(lotes[i]));
	   }
	   
	   assertEquals(driver.findAll().size(), 7);
	   
	   driver.delete(mateus);
	   
	   assertEquals(driver.findAll().size(), 6);
	   
	   
	   
   }
   
   @Test
   @DisplayName("Deleta 2 lotes de produtos no repositório")
   void deletaDoisLotes() {
	   Lote[] lotes = {lote, miguel, mateus, sergio, evaldo, gabriel, emile};
	   List<Lote> result = new ArrayList<>();
	   
	   assertEquals(driver.findAll().size(), 0);
	   
	   for (int i = 0; i < 7; i++) {
		   result.add(driver.save(lotes[i]));
	   }
	   
	   assertEquals(driver.findAll().size(), 7);
	   
	   driver.delete(mateus);
	   
	   assertEquals(driver.findAll().size(), 6);
	   
	   driver.delete(miguel);
	   
	   assertEquals(driver.findAll().size(), 5);
	   
   }

   @Test
   @DisplayName("Deleta todos lotes de produtos no repositório")
   void deletaTodosLotes() {
	   Lote[] lotes = {lote, miguel, mateus, sergio, evaldo, gabriel, emile};
	   List<Lote> result = new ArrayList<>();
	   
	   assertEquals(driver.findAll().size(), 0);
	   
	   for (int i = 0; i < 7; i++) {
		   result.add(driver.save(lotes[i]));
	   }
	   
	   assertEquals(driver.findAll().size(), 7);
	   
	   driver.deleteAll();
	   
	   assertEquals(driver.findAll().size(), 0);
	   
   }
   
}