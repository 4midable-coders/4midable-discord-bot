package com.fourMidableCoders.fourMidableDiscordBot.service;

import uz.khurozov.jokeapi.JokeApi;
import uz.khurozov.jokeapi.constant.Category;
import uz.khurozov.jokeapi.constant.Flag;
import uz.khurozov.jokeapi.constant.Lang;
import uz.khurozov.jokeapi.constant.Type;
import uz.khurozov.jokeapi.dto.Joke;
import uz.khurozov.jokeapi.dto.JokeFilter;

import java.util.Set;


//This class is used to get a joke from the JokeAPI.
public class JokeService {

    public static String getJoke() {
        JokeApi jokeApi = new JokeApi();

        // Forming filter:
        JokeFilter filter = new JokeFilter.Builder()
                .category(Category.Any)
                .blacklistFlags(Set.of(Flag.nsfw, Flag.racist, Flag.sexist, Flag.explicit))
                .lang(Lang.en)
                .idRange(0, 305)
                .type(Type.twopart)
                .build();

        // Requesting joke(s):
        Joke joke = jokeApi.getJoke(filter);

        return joke.jokeString();

    }
}
