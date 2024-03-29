# FilmQueryProject
	
# Film Query Lab and Homework Project!

#### Technologies Used
	- JAVA
	- Eclipse
	- Git/GitHub
	- Sublime Text Editor
	- Zsh
	- MySQL
	- DAO, JDBC
	- JUnit Tests
	
	
#### Lessons Learned
	- Using a shared connection at the DatabaseAccessorObject.
	- Protecting against too many open connections.
	- Need for more abstraction and shared code.
	
#### Notes

JUnit test(s) have been implemented for retrieval and compare the results as if the DB was freshly created and populated with the starting data.  

This means, for actorId = 1, there are 19 films that are retrieved.

```
	@Test
	void test_getFilmsForActorId_returns_19_for_id_one() {
		List<Film> films = db.findFilmsByActorId(1);
		assertEquals(19, films.size());
	}
```

This also means that the results for getting actorId = 2 is as follows.
- id=2
- firstName=Nick
- lastName=Wahlberg

```
	@Test
	void test_getActorById_returns_actor_with_id() {
		Actor a = db.findActorById(2);

		// id=2
		// firstName=Nick
		// lastName=Wahlberg

		assertAll("Actor", () -> assertEquals(2, a.getId()), () -> assertEquals("Nick", a.getFirstName()),
				() -> assertEquals("Wahlberg", a.getLastName()));

	}
```

```
package com.skilldistillery.filmquery.database;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

class DatabaseAccessTests {
	private DatabaseAccessor db;

	@BeforeEach
	void setUp() throws Exception {
		db = new DatabaseAccessorObject();
	}

	@AfterEach
	void tearDown() throws Exception {
		db = null;
	}

	@Test
	void test_getFilmById_returns_film_with_id() {
		Film f = db.findFilmById(1);
		assertEquals("ACADEMY DINOSAUR", f.getTitle());
	}

	@Test
	void test_getFilmById_with_invalid_id_returns_null() {
		Film f = db.findFilmById(-42);
		assertNull(f);
	}

	@Test
	void test_getActorById_returns_actor_with_id() {
		Actor a = db.findActorById(2);

		// id=2
		// firstName=Nick
		// lastName=Wahlberg

		assertAll("Actor", () -> assertEquals(2, a.getId()), () -> assertEquals("Nick", a.getFirstName()),
				() -> assertEquals("Wahlberg", a.getLastName()));

	}

	@Test
	void test_getActorsForFilmByFilmId_returns_not_null_for_id_one() {
		List<Actor> actors = db.findActorsByFilmId(1);
		assertNotNull(actors);
	}

	@Test
	void test_getActorsForFilmByFilmId_returns_ten_for_id_one() {
		List<Actor> actors = db.findActorsByFilmId(1);
		assertEquals(10, actors.size());
	}

	@Test
	void test_getFilmsForActorId_returns_19_for_id_one() {
		List<Film> films = db.findFilmsByActorId(1);
		assertEquals(19, films.size());
	}

}

```

![JUnit Screenshot](junit.png)

#### User Stories

User Stories

User Story 1 (COMPLETED)

The user is presented with a menu in which they can choose to:

- Look up a film by its id.
- Look up a film by a search keyword.
- Exit the application.

User Story 2 (COMPLETED)

- If the user looks up a film by id, they are prompted to enter the film id. If the film is not found, they see a message saying so. If the film is found, its title, year, rating, and description are displayed.

User Story 3 (COMPLETED)

- If the user looks up a film by search keyword, they are prompted to enter it. If no matching films are found, they see a message saying so. Otherwise, they see a list of films for which the search term was found anywhere in the title or description, with each film displayed exactly as it is for User Story 2.

User Story 4 (COMPLETED)

- When a film is displayed, its language (English,Japanese, etc.) is displayed, in addition to the film's title, year, rating, and description.

User Story 5 (COMPLETED)

- When a film is displayed, the list of actors in its cast is displayed, in addition to the film's title, year, rating, description, and language.

Stretch Goals

Goal 1
- When a film is displayed, the user can choose from a submenu whether to:
- Return to the main menu.
- View all film details.

- If they choose to view all details, they will see all column values of the film record they chose.

Goal 2

- When viewing film details, the user will see a list of all the film's categories (Family, Comedy, etc.) for the film.

Goal 3

- When viewing film details, the user will see a list of all copies of the film in inventory, with their condition.

Goal 4 (COMPLETED)

- Write JUnit tests for DatabaseAccessorObject's methods.

