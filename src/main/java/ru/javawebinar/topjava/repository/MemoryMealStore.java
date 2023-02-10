package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Oywayten 06.02.2023.
 */
public class MemoryMealStore implements MealStore {

    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);

    public MemoryMealStore() {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal findById(int id) {
        return mealMap.get(id);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() == null) {
            meal = new Meal(id.getAndIncrement(), meal);
        }
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal delete(int id) {
        return mealMap.remove(id);
    }
}
