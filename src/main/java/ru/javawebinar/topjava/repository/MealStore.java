package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Oywayten 07.02.2023.
 */
public interface MealStore {

    Meal findById(int id);

    List<Meal> findAll();

    Meal save(Meal meal);

    Meal delete(int id);
}
