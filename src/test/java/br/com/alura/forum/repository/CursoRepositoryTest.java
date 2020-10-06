package br.com.alura.forum.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.alura.forum.models.Curso;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // teste padrão com bd em memória, anotação para mudar
@ActiveProfiles("test")
class CursoRepositoryTest {

	@Autowired
	private CursoRepository rep;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	public void testFindByNome() {
		String nomeCurso = "Spring Boot";
		
		//populando banco de testes vazio
		Curso springBoot = new Curso();
		springBoot.setNome(nomeCurso);
		springBoot.setCategoria("Programação");
		em.persist(springBoot);
		
		Curso curso = rep.findByNome(nomeCurso);
		Assert.assertNotNull(curso);
		Assert.assertEquals(nomeCurso, curso.getNome());
	}
}
