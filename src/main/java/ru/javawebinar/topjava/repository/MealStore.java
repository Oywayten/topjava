package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Oywayten 07.02.2023.
 */
public interface MealStore {
    Meal findById(int id);

    List<Meal> findAll();

    Meal add(LocalDateTime lt, String ds, int cal);

    Meal update(int id, LocalDateTime lt, String ds, int cal);

    Meal delete(int id);
}
