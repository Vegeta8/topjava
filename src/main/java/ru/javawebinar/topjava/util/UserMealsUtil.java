package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        /*List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);*/

       System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        List<UserMeal> mealsInSpecificTime = new ArrayList<>();
        int sum = 0;
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                sum += meal.getCalories();
                mealsInSpecificTime.add(meal);
            }
        }
        for (UserMeal mealInPeriod :
                mealsInSpecificTime) {
            if (sum > caloriesPerDay) {
                userMealWithExcesses.add(new UserMealWithExcess(mealInPeriod.getDateTime(),
                        mealInPeriod.getDescription(), mealInPeriod.getCalories(), true));
            } else {
                userMealWithExcesses.add(new UserMealWithExcess(mealInPeriod.getDateTime(),
                        mealInPeriod.getDescription(), mealInPeriod.getCalories(), false));
            }
        }

        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map <LocalDateTime, Integer> dateTimeIntegerMap = meals.stream().collect(
                Collectors.groupingBy(UserMeal::getDateTime, Collectors.summingInt(UserMeal::getCalories))
        );
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(LocalTime.from(meal.getDateTime()), startTime, endTime))
                .map(meal -> newMeal(meal, dateTimeIntegerMap.get(meal.getDateTime()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
    public static UserMealWithExcess newMeal(UserMeal meal, boolean excess) {
        return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
