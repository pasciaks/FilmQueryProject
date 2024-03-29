package com.skilldistillery.filmquery.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.skilldistillery.filmquery.database.DatabaseAccessorObject;

public class Film {

	int id;
	String title;
	String description;
	int releaseYear;
	int languageId;
	int rentalDuration;
	double rentalRate;
	int length;
	double replacementCost;
	String rating;
	String specialFeatures;
	String language;

	List<Actor> actorsInFilm = new ArrayList<>();

	public Film() {
		super();
	}

	public Film(int id, String title, String description, int releaseYear, int languageId, int rentalDuration,
			double rentalRate, int length, double replacementCost, String rating, String specialFeatures,
			String language) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.releaseYear = releaseYear;
		this.languageId = languageId;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.length = length;
		this.replacementCost = replacementCost;
		this.rating = rating;
		this.specialFeatures = specialFeatures;
		this.language = language;

	}

	public List<Actor> getActorsForFilm(DatabaseAccessorObject db) {
		this.actorsInFilm = db.findActorsByFilmId(this.id);
		return this.actorsInFilm;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public int getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public double getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(double rentalRate) {
		this.rentalRate = rentalRate;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getReplacementCost() {
		return replacementCost;
	}

	public void setReplacementCost(double replacementCost) {
		this.replacementCost = replacementCost;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSpecialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String toShortString() {
		StringBuilder builder = new StringBuilder();
		builder.append(title + " (" + releaseYear + "/" + rating + ") ");
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nFilm id = ");
		builder.append(id);
		builder.append("\n               title = ");
		builder.append(title);
		builder.append("\n         description = ");
		builder.append(description);
		builder.append("\n         releaseYear = ");
		builder.append(releaseYear);
		// builder.append("\n languageId = ");
		// builder.append(languageId);
		// builder.append("\n rentalDuration = ");
		// builder.append(rentalDuration);
		// builder.append("\n rentalRate = ");
		// builder.append(rentalRate);
		// builder.append("\n length = ");
		// builder.append(length);
		// builder.append("\n replacementCost = ");
		// builder.append(replacementCost);
		builder.append("\n              rating = ");
		builder.append(rating);
		// builder.append("\n specialFeatures = ");
		// builder.append(specialFeatures);
		builder.append("\n            language = ");
		builder.append(language);
		builder.append("\n");
		builder.append("\nActors in Film: \n");
		for (Actor a : this.actorsInFilm) {
			builder.append(a.toString());
		}

		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, language, languageId, length, rating, releaseYear, rentalDuration,
				rentalRate, replacementCost, specialFeatures, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(language, other.language) && languageId == other.languageId && length == other.length
				&& Objects.equals(rating, other.rating) && releaseYear == other.releaseYear
				&& rentalDuration == other.rentalDuration
				&& Double.doubleToLongBits(rentalRate) == Double.doubleToLongBits(other.rentalRate)
				&& Double.doubleToLongBits(replacementCost) == Double.doubleToLongBits(other.replacementCost)
				&& Objects.equals(specialFeatures, other.specialFeatures) && Objects.equals(title, other.title);
	}

}
