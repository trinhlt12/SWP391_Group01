package com.embanthe.util;

public class CategoryValidator {

    public static String validate(String name) {

        if (name == null || name.trim().isEmpty()) {
            return "Category name is required";
        }

        if (name.length() < 3) {
            return "Category name must be at least 3 characters";
        }

        if (name.length() > 50) {
            return "Category name must not exceed 50 characters";
        }

        return null; // hợp lệ
    }
}
