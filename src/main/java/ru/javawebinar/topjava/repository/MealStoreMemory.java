package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Oywayten 06.02.2023.
 */
public class MealStoreMemory implements MealStore {
    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);

    private MealStoreMemory() {
        mealMap.put(1, new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealMap.put(2, new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealMap.put(3, new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealMap.put(4, new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealMap.put(5, new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealMap.put(6, new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealMap.put(7, new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public static MealStoreMemory getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final MealStoreMemory INSTANCE = new MealStoreMemory();
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
    public Meal add(LocalDateTime lt, String ds, int cal) {
        Meal meal = new Meal(id.getAndIncrement(), lt, ds, cal);
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal update(int id, LocalDateTime lt, String ds, int cal) {
        Meal meal = new Meal(id, lt, ds, cal);
        return Optional.ofNullable(mealMap.replace(id, meal)).orElseThrow(() -> new IllegalArgumentException("Illegal id of meal"));
    }

    @Override
    public Meal delete(int id) {
        return Optional.of(mealMap.remove(id)).orElseThrow(() -> new IllegalArgumentException("Illegal id of meal"));
    }
}
