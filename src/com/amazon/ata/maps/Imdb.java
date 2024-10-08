package com.amazon.ata.maps;

import java.util.*;

/**
 * Stores the relationships between movies and actors, allowing releasing a new movie
 * with all actors in the cast, adding a single actor to an existing (or new) movie,
 * unreleasing a movie completely, and querying actors by movie and vice versa.
 */
public class Imdb {
    Map<Movie, Set<Actor>> movieActorMap = new HashMap<>();
    /**
     * Adds the new movie to the set of movies that an actor has appeared in.
     * If the movie already exists in the database, this will overwrite actors
     * associated with the movie with the new values provided.
     *
     * @param movie the movie being released
     * @param actors a set of actors that appear in the movie
     */
    public void releaseMovie(Movie movie, Set<Actor> actors) {
        movieActorMap.put(movie, actors);
    }

    /**
     * Removes the given movie from the database, including any actors
     * credited in the movie.
     *
     * @param movie the movie to remove
     * @return true if the movie was removed, false if it wasn't in Imdb
     *         to begin with
     */
    public boolean removeMovie(Movie movie) {
        return movieActorMap.remove(movie) != null;
    }

    /**
     * Adds a new movie to the set of movies that an actor has appeared in.
     * If the movie already exists in the database, will add the actor
     * if they haven't been added already. If the movie doesn't yet exist
     * in the database, this will add the movie with the actor as the only
     * credit.
     *
     * @param movie the movie to add to the actors set of movies
     * @param actor the actor that appears in this movie
     */
    public void tagActorInMovie(Movie movie, Actor actor) {
        if (movieActorMap.containsKey(movie)) {
            movieActorMap.get(movie).add(actor);
            return;
        }
        movieActorMap.put(movie, new HashSet<>());
        movieActorMap.get(movie).add(actor);
    }

    /**
     * Returns a set of actors who are credited in the given movie. If a movie is not
     * released on IMDB throw an IllegalArgumentException.
     *
     * @param movie the movie to get actors for
     * @return the set of actors who are credited in the passed in movie
     */
    public Set<Actor> getActorsInMovie(Movie movie) {

        Set<Actor> actors = movieActorMap.get(movie);

        if (actors == null) {
            throw new IllegalArgumentException("This movie is not released on IMDB");
        }

        return actors;
    }

    /**
     * Returns a set of movies that the specified actor has appeared in. If the
     * actor has not appeared in any movies, return an empty Set.
     *
     * @param actor the actor to get movies for
     * @return the set of movies that the passed in actor has appeared in
     */
    public Set<Movie> getMoviesForActor(Actor actor) {
        Set<Movie> appeardMovies = new HashSet<>();

        for (Map.Entry<Movie, Set<Actor>> entry : movieActorMap.entrySet()) {
            if (entry.getValue().contains(actor)) {
                appeardMovies.add(entry.getKey());
            }
        }
        return appeardMovies;
    }

    /**
     * Returns all actors that IMDB has in its records as having appeared in a movie.
     *
     * @return a set of actors that IMDB has as appeared in movies
     */
    public Set<Actor> getAllActorsInIMDB() {
        Set<Actor> allActors = new HashSet<>();

        for (Set<Actor> actors : movieActorMap.values()) {
            allActors.addAll(actors);
        }
        return allActors;
    }

    /**
     * Returns the total number of individual movie-actor pairs in the database.
     *
     * So if there are 2 movies, the first movie has 1 actor and the second one
     * has 6 actors, this method will return 7.
     *
     * @return The total number of movie-actor pairings: the number of times
     *         any actor has appeared in any movie
     */
    public int getTotalNumCredits() {
        int totalNumCredits = 0;

        for (Set<Actor> actors : movieActorMap.values()) {
            totalNumCredits = totalNumCredits + actors.size();
        }

        return totalNumCredits;
    }
}
