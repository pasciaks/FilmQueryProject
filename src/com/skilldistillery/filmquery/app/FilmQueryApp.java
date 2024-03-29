package com.skilldistillery.filmquery.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	/*
	 * 
	 * Labs FilmQuery/com.skilldistillery.filmquery.entities
	 * 
	 * [x] Complete the Film class with attributes that map to the columns of the
	 * film table.
	 * 
	 * [x] Use appropriate data types and Java naming conventions. Provide getters
	 * and setters, and appropriate constructors. Also add a good toString, and
	 * equals and hashCode methods.
	 * 
	 * [x] Define an Actor class with id, first name, and last name fields. Include
	 * the usual getters, setters, toString, etc (of course!), and appropriate
	 * constructor(s).
	 * 
	 * FilmQuery/com.skilldistillery.filmquery.database.DatabaseAccessor
	 * 
	 * [x] With the Actor class implemented, uncomment the two commented methods in
	 * the DatabaseAccessor interface.
	 * 
	 * FilmQuery/com.skilldistillery.filmquery.database.DatabaseAccessorObject
	 * 
	 * [x] All JDBC code will be in methods of the DatabaseAccessorObject class.
	 * 
	 * [x] Implement the findFilmById method that takes an int film ID, and returns
	 * a Film object (or null, if the film ID returns no data.)
	 * 
	 * [x] Implement findActorById method that takes an int actor ID, and returns an
	 * Actor object (or null, if the actor ID returns no data.)
	 * 
	 * [x] Implement findActorsByFilmId with an appropriate List implementation that
	 * will be populated using a ResultSet and returned.
	 * 
	 * [x] Make sure your JDBC code uses PreparedStatement with bind variables
	 * instead of concatenating values into SQL strings.
	 * 
	 * [x] Modify your Film class to include a List of actors for the film's cast.
	 * 
	 * [x] When a film is retrieved from the database, its actors are also retrieved
	 * and included in the Film object.
	 * 
	 * FilmQuery/com.skilldistillery.filmquery.app.FilmQueryApp
	 * 
	 * Test your code by running FilmQueryApp.
	 * 
	 */

	private DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {

		FilmQueryApp app = new FilmQueryApp();

		boolean connected = ((DatabaseAccessorObject) app.db).openConnection();

		if (connected) {
			// app.testFindAllActors();
			// app.testFindAllFilms("This film does not exist.");
			// app.testGetActorByActorId(1);
			// app.testGetActorByActorId(-1);
			// app.testGetFilmAndActorsForFilmByFilmId(1);
			// app.testGetFilmAndActorsForFilmByFilmId(555);
			// app.testGetFilmAndActorsForFilmByFilmId(-9);
			// app.testGetFilmById(1);
			// app.testGetFilmById(5551);
			// app.testGetFilmAndActorsForFilmByFilmId(0);
			// app.testGetFilmsForActorId(1);
		} else {
			System.out.println("Database connection failed.");
		}

		if (connected) {
			app.launch();
		} else {
			System.out.println("\nDatabase connection failed.");
		}

		boolean isClosed = ((DatabaseAccessorObject) app.db).closeConnection();

		System.out.println("Database connection closed: " + isClosed);

		System.err.println("\n\nGoodbye.\n");

	}

	private void testFindAllActors() {

		List<Actor> actors = db.findAllActors();
		if (actors == null || actors.isEmpty()) {
			System.out.println("No actors found.");
		} else {
			System.out.println("Actors found: " + actors.size() + "\n");
			for (Actor actor : actors) {
				// if (actor.getId() == 1) {
				System.out.println(actor.toString());
				// }
			}
		}
	}

	private void testFindAllFilms(String keyword) {

		List<Film> films = db.findAllFilms();

		List<Film> foundFilms = new ArrayList<>();

		if (films == null || films.isEmpty()) {

			System.err.println("\nNo films found from database.");

		} else {

			// System.out.println("Films found: " + films.size() + "\n");

			for (Film film : films) {

				if (film.getTitle().toLowerCase().contains(keyword.toLowerCase())) {

					foundFilms.add(film);

				} else if (film.getDescription().toLowerCase().contains(keyword.toLowerCase())) {

					foundFilms.add(film);

				} else {

					// no match this iteration

				}
			}
		}

		if (foundFilms.isEmpty()) {
			System.err.println("\nNo films found for keyword: " + keyword + "\n");
			return;
		}

		for (Film film : foundFilms) {
			System.out.println(film.toString());
		}

		System.err.println("\nTotal of " + foundFilms.size() + " Films found for keyword: " + keyword + "\n");

	}

	private void testFindAllFilms() {

		List<Film> films = db.findAllFilms();
		if (films == null || films.isEmpty()) {
			System.out.println("No films found.");
		} else {
			System.out.println("Films found: " + films.size() + "\n");
			for (Film film : films) {
				// if (film.getId() == 1) {
				System.out.println(film.toString());
				// }
			}
		}
	}

	private void testGetFilmById(int filmId) {

		Film film = db.findFilmById(filmId);
		if (film == null || film.getId() == 0) {
			System.out.println("Film not found for filmId = " + filmId);
		} else {
			System.out.println(film.toString());
		}

	}

	private void testGetActorByActorId(int actorId) {

		Actor actor = db.findActorById(actorId);
		if (actor == null || actor.getId() == 0) {
			System.out.println("Actor not found.");
		} else {
			System.out.println(actor.toString());
		}

	}

	private void testGetFilmAndActorsForFilmByFilmId(int filmId) {

		Film film = db.findFilmById(filmId);

		if (film == null || film.getId() == 0) {

			System.out.println("Film not found for filmId = " + filmId);

		} else {

			System.out.println(film.toString());

		}

	}

	private void testGetFilmsForActorId(int actorId) {

		List<Film> filmsForActor = db.findFilmsByActorId(actorId);

		if (filmsForActor == null || filmsForActor.isEmpty() || filmsForActor.size() == 0) {
			System.out.println("Actor or Films for Actor not found...");
			return;
		} else {
			System.out.println("Actor: " + actorId + " has appeared in " + filmsForActor.size() + " films.");
		}

		for (Film film2 : filmsForActor) {
			System.out.println(film2.toString());
		}

	}

	private void launch() {

		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {

		boolean keepGoing = true;

		while (keepGoing) {

			try {

				System.out.println("Please select from the following options:");
				System.out.println("1. Look up a film by its ID.");
				System.out.println("2. Look up a film by a keyword search.");
				System.out.println("3. Exit the application.");
				System.err.print("\nEnter your selection: ");

				int userChoice = Integer.parseInt(input.nextLine());

				switch (userChoice) {
				case 1:
					System.out.println("\nPlease enter the film ID:");
					int filmId = Integer.parseInt(input.nextLine());
					testGetFilmById(filmId);
					break;
				case 2:
					System.out.println("\nPlease enter a keyword to search for:");
					String keyword = input.nextLine();
					testFindAllFilms(keyword);
					// TODO - convert to like query instead of filtering from all
					break;
				case 3:
					System.out.println("\nExiting the application.");
					keepGoing = false;
					break;
				default:
					System.err.println("\nInvalid selection. Please try again.");
					break;
				}

			} catch (Exception e) {
				System.err.println("\nInvalid input. Please try again.");
				continue;
			}

		}

	}

}
