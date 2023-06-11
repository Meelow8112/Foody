package com.example.foody.validator;

import com.example.foody.entity.Category;
import com.example.foody.validator.annotation.ValidCategoryId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCategoryIdValidator implements ConstraintValidator<ValidCategoryId, Category> {
        @Override
        public boolean isValid(Category category, ConstraintValidatorContext context) {
                return category != null && category.getId() != null;
        }

}