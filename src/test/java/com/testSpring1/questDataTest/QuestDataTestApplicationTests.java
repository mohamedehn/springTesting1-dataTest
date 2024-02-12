package com.testSpring1.questDataTest;

import com.testSpring1.questDataTest.entity.Fire;
import com.testSpring1.questDataTest.entity.Fireman;
import com.testSpring1.questDataTest.repository.FireRepository;
import com.testSpring1.questDataTest.repository.FiremanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// dit à Spring Data que c'est un test de data et qu'il faut initialiser la fausse base de données
@DataJpaTest
class DataTests {


		// on peut ensuite injecter un repository et l'utiliser normalement
		@Autowired
		FireRepository fireRepository;
		@Autowired
		FiremanRepository firemanRepository;

		//Test d'ajout d'un fire en BD
		@Test
		public void testCreateFire() {
			int severity = 8;
			Instant date = Instant.now();
			var fire = new Fire(severity, date);

			// flush envoie les données instantanément à la base
			// permettant au findById de récuperer les valeurs actualisées
			fireRepository.saveAndFlush(fire);

			Optional<Fire> fromDB = fireRepository.findById(fire.getId());

			assertTrue(fromDB.isPresent());
			assertEquals(fire.getId(), fromDB.get().getId());
			assertEquals(date, fromDB.get().getDate());
			assertEquals(severity, fromDB.get().getSeverity());
		}

		//ajoute de plusieurs feux en BD + ajout pompier liés à ces feux
		@Test
		public void testFiresWithFireman() {
			Instant date = Instant.now();

			int severity = 5;
			var fire = new Fire(severity, date);

			int severity2 = 8;
			var fire2 = new Fire(severity2, date);

			int severity3 = 3;
			var fire3 = new Fire(severity3, date);

			String name = "Jean";
			var fireman = new Fireman(name);

			fireRepository.saveAndFlush(fire);
			fireRepository.saveAndFlush(fire2);
			fireRepository.saveAndFlush(fire3);
			firemanRepository.saveAndFlush(fireman);

			fire.setFireman(fireman);
			fire2.setFireman(fireman);
			fire3.setFireman(fireman);

			Optional <Fireman> fromDB = firemanRepository.findById(fireman.getId());

			assertTrue(fromDB.isPresent());
			assertEquals(fireman.getId(), fromDB.get().getId());
			assertEquals(name, fromDB.get().getName());
			assertEquals(0, fromDB.get().getFires().size());
		}

		//création des firemans & fires dans une méthode privée accessible uniquement dans la classe de test.
		//Patron de conception builder
		public class FiremanBuilder {
			private Long id;
			private String name;

			public FiremanBuilder withId(Long id) {
				this.id = id;
				return this;
			}

			public FiremanBuilder withName(String name) {
				this.name = name;
				return this;
			}

			public Fireman build() {
				Fireman fireman = new Fireman();
				fireman.setId(this.id);
				fireman.setName(this.name);
				return fireman;
			}
		}

		public class FireBuilder{
			private Long id;
			private int severity;
			private Instant date;
			private Fireman fireman;

			public FireBuilder withId(Long id) {
				this.id = id;
				return this;
			}

			public FireBuilder withSeverity(int severity) {
				this.severity = severity;
				return this;
			}

			public FireBuilder withDate(Instant date) {
				this.date = date;
				return this;
			}

			public Fire build() {
				Fire fire = new Fire();
				fire.setId(this.id);
				fire.setSeverity(this.severity);
				fire.setDate(this.date);
				fire.setFireman(this.fireman);
				return fire;
			}

			public FireBuilder withFireman(Fireman fireman){
				this.fireman = fireman;
				return this;
			}
		}


		private Fireman buildFireman(String name) {
			return new FiremanBuilder().withName(name).build();
		}

		private Fire buildFire(int severity, Fireman fireman) {
			return new FireBuilder().withSeverity(severity).withFireman(fireman).build();
		}

		//Plusieurs firemans et fires en BD, on test celui qui as le plus de fires et on le log
		@Test
		public void testFiremanVeterans1(){
			Instant date = Instant.now();

			var fire = new FireBuilder().withSeverity(3).build();
			var fire2 = new FireBuilder().withSeverity(8).build();
			var fire3 = new FireBuilder().withSeverity(5).build();

			Fireman fireman = new FiremanBuilder().withName("Jean").build();
			Fireman fireman2 = new FiremanBuilder().withName("Paul").build();
			Fireman fireman3 = new FiremanBuilder().withName("Jacques").build();

			fireRepository.saveAndFlush(fire);
			fireRepository.saveAndFlush(fire2);
			fireRepository.saveAndFlush(fire3);
			firemanRepository.saveAndFlush(fireman);
			firemanRepository.saveAndFlush(fireman2);
			firemanRepository.saveAndFlush(fireman3);

			fire.setFireman(fireman);
			fire2.setFireman(fireman3);
			fire3.setFireman(fireman);

			Optional <Fireman> fromDB = firemanRepository.getVeteran();
			if (fromDB.isPresent()) {
				System.out.println("Veteran's name: " + fromDB.get().getName());
			} else {
				System.out.println("No veteran found.");
			}
		}

		//Un seul pompier et plusieurs feux, via getVeteran() on récupère le seul pompier présent
		@Test
		public void testOneFireman() {
			var fire = new FireBuilder().withSeverity(3).build();
			var fire2 = new FireBuilder().withSeverity(8).build();
			var fire3 = new FireBuilder().withSeverity(5).build();

			Fireman fireman = new FiremanBuilder().withName("Jacques").build();

			fireRepository.saveAndFlush(fire);
			fireRepository.saveAndFlush(fire2);
			fireRepository.saveAndFlush(fire3);
			firemanRepository.saveAndFlush(fireman);


			fire.setFireman(fireman);
			fire2.setFireman(fireman);
			fire3.setFireman(fireman);

			Optional <Fireman> fromDB = firemanRepository.getVeteran();
			if (fromDB.isPresent()) {
				System.out.println("Veteran's name: " + fromDB.get().getName());
			} else {
				System.out.println("No veteran found.");
			}
		}

		//Test avec aucun pompier dans la base
		@Test
		public void testWithNoFireman() {
			var fire = new FireBuilder().withSeverity(3).build();
			var fire2 = new FireBuilder().withSeverity(8).build();
			var fire3 = new FireBuilder().withSeverity(5).build();

			fireRepository.saveAndFlush(fire);
			fireRepository.saveAndFlush(fire2);
			fireRepository.saveAndFlush(fire3);

			Optional <Fireman> fromDB = firemanRepository.getVeteran();
			if (fromDB.isPresent()) {
				System.out.println("Veteran's name: " + fromDB.get().getName());
			} else {
				System.out.println("No veteran found.");
			}
		}


}


