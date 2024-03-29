package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

// This statement is to remind everyone to never use the Statement class

// That statement is to remind everyone to instead use the PreparedStatement class

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid";
	private static final String user = "student";
	private static final String pass = "student";

	private Connection conn = null;

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, user, pass);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {

		}
	}

	public boolean openConnection() {
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			if (!conn.isClosed()) {
				return true;
			}
		} catch (SQLException e) {
			System.err.println("Error connecting to database...");
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public boolean closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				if (conn.isClosed()) {
					return true;
				}
			} catch (SQLException e) {
				System.err.println("Error closing connection...");
				return false;
			}
		}
		return false;
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;

		String sql = "select film.*, l.`name` as language from film join `language` l on l.id  = film.language_id  where film.id = ?;";

		PreparedStatement stmt = null;

		ResultSet filmResult = null;

		try {

			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, filmId);

			filmResult = stmt.executeQuery();

			if (filmResult.next()) {

				film = new Film();

				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getInt("release_year"));
				film.setLanguageId(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				film.setLanguage(filmResult.getString("language"));

				film.getActorsForFilm((DatabaseAccessorObject) this);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			try {
				if (stmt != null) {
					stmt.close();
				}
				if (filmResult != null) {
					filmResult.close();
				}
			} catch (SQLException e) {
				System.err.println("Error closing stmt, filmResult...");
			}

		}

		return film;
	}

	@Override
	public Actor findActorById(int actorId) {

		Actor actor = null;

		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";

		PreparedStatement stmt = null;

		ResultSet actorResult = null;

		try {

			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, actorId);

			actorResult = stmt.executeQuery();

			if (actorResult.next()) {

				actor = new Actor();

				actor.setId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			try {
				if (stmt != null) {
					stmt.close();
				}
				if (actorResult != null) {
					actorResult.close();
				}
			} catch (SQLException e) {
				System.err.println("Error closing stmt, actorResult...");
			}

		}

		return actor;

	}

	public List<Film> findFilmsByActorId(int actorId) {

		List<Film> films = new ArrayList<>();

		try {

			String sql = "SELECT film.id, film.title, film.description, film.release_year, film.language_id, film.rental_duration, ";
			sql += " film.rental_rate, film.length, film.replacement_cost, film.rating, film.special_features, `language`.name as language "
					+ " FROM film JOIN film_actor ON film.id = film_actor.film_id "
					+ " JOIN `language` on `language`.id = film.language_id WHERE actor_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, actorId);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Film film = new Film();

				film.setId(rs.getInt("id"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setReleaseYear(rs.getInt("release_year"));
				film.setLanguageId(rs.getInt("language_id"));
				film.setRentalDuration(rs.getInt("rental_duration"));
				film.setRentalRate(rs.getDouble("rental_rate"));
				film.setLength(rs.getInt("length"));
				film.setReplacementCost(rs.getDouble("replacement_cost"));
				film.setRating(rs.getString("rating"));
				film.setSpecialFeatures(rs.getString("special_features"));
				film.setLanguage(rs.getString("language"));

				film.getActorsForFilm((DatabaseAccessorObject) this); // Check, might be a double call...

				films.add(film);
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return films;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {

		List<Actor> actorsByFilmId = new ArrayList<>();

		String sql = "select actor.id, actor.first_name, actor.last_name, film.id, film.title, film.description from film join film_actor on film.id = film_actor.film_id join actor on film_actor.actor_id = actor.id where film_id = ?";

		Actor actor = null;

		PreparedStatement stmt = null;

		ResultSet actorResult = null;

		try {

			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, filmId);

			actorResult = stmt.executeQuery();

			while (actorResult.next()) {

				actor = new Actor();

				actor.setId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));

				actorsByFilmId.add(actor);
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			try {
				if (stmt != null) {
					stmt.close();
				}
				if (actorResult != null) {
					actorResult.close();
				}
			} catch (SQLException e) {
				System.err.println("Error closing stmt, actorResult...");
			}

		}

		return actorsByFilmId;

	}

	@Override
	public List<Film> findAllFilms(String searchKeyword) {

		List<Film> allFilms = new ArrayList<>();

		searchKeyword = searchKeyword.trim();

		String sql = "select film.*, l.`name` as language from film join `language` l on l.id  = film.language_id ";

		String keywordFilter = " WHERE film.title LIKE ? OR film.description LIKE ? ";

		if (searchKeyword != null && !searchKeyword.isEmpty()) {
			sql += keywordFilter;
		}

		Film film = null;

		PreparedStatement stmt = null;

		ResultSet filmResult = null;

		try {

			stmt = conn.prepareStatement(sql);

			if (searchKeyword != null && !searchKeyword.isEmpty()) {
				stmt.setString(1, "%" + searchKeyword + "%");
				stmt.setString(2, "%" + searchKeyword + "%");
			}

			filmResult = stmt.executeQuery();

			while (filmResult.next()) {

				film = new Film();

				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getInt("release_year"));
				film.setLanguageId(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				film.setLanguage(filmResult.getString("language"));

				film.getActorsForFilm((DatabaseAccessorObject) this);

				allFilms.add(film);
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			try {
				if (stmt != null) {
					stmt.close();
				}
				if (filmResult != null) {
					filmResult.close();
				}
			} catch (SQLException e) {
				System.err.println("Error closing stmt, filmResult...");
			}

		}

		return allFilms;
	}

	@Override
	public List<Actor> findAllActors() {

		List<Actor> allActors = new ArrayList<>();

		String sql = "SELECT id, first_name, last_name FROM actor";

		Actor actor = null;

		PreparedStatement stmt = null;

		ResultSet actorResult = null;

		try {

			stmt = conn.prepareStatement(sql);

			actorResult = stmt.executeQuery();

			while (actorResult.next()) {

				actor = new Actor();

				actor.setId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));

				allActors.add(actor);
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			try {
				if (stmt != null) {
					stmt.close();
				}
				if (actorResult != null) {
					actorResult.close();
				}
			} catch (SQLException e) {
				System.err.println("Error closing stmt, actorResult...");
			}

		}

		return allActors;
	}

}
