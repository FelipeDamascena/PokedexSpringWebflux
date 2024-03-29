package com.webflux.pokedex;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import com.webflux.pokedex.com.repository.PokemonRepository;
import com.webflux.pokedex.com.model.Pokemon;

import reactor.core.publisher.Flux;


@SpringBootApplication
public class PokedexApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args); }

	
	@Bean
	CommandLineRunner init (ReactiveMongoOperations operations, PokemonRepository repository) {
		return args -> {
			Flux<Pokemon> pokemonFlux = Flux.just(
					new Pokemon(null, "Bulbassauro", "Semente", "OverGrow", 6.09),
					new Pokemon(null, "Charizard", "Fogo", "Blaze", 90.05),
					new Pokemon(null, "Caterpie", "Minhoca", "Poeira do Escudo", 2.09),
					new Pokemon(null, "Blastoise", "Marisco", "Torrente", 85.9))
					.flatMap(repository::save);

			pokemonFlux
					.thenMany(repository.findAll())
					.subscribe(System.out::println);
		};
	}
}
