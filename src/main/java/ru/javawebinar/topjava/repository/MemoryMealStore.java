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
        MealsUtil.meals.forEach(this::add);
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
    public Meal add(Meal meal) {
        Meal result = new Meal(id.getAndIncrement(), meal);
        mealMap.put(result.getId(), result);
        return result;
    }

    @Override
    public Meal update(Meal meal) {
        Meal result = mealMap.replace(meal.getId(), meal);
        return result != null ? meal : null;
    }

    @Override
    public Meal delete(int id) {
        return mealMap.remove(id);
    }
}
