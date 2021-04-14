package lip.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lip.model.Music;

public class CreateAPI {

	public static void main(String[] args) {
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("lip");
			EntityManager em = emf.createEntityManager();

			Music music = new Music();
			music.setTitle("Desenho");
			music.setLyrics("Letra insana de bom, meu bom.");
			
			em.getTransaction().begin();
			em.persist(music);
			em.getTransaction().commit();
			
			System.out.println("Ready!");			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not ready!");
		}
	}
}
