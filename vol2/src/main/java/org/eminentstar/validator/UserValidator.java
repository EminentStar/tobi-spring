package org.eminentstar.validator;

import org.eminentstar.mvc.model.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
  @Override
  public boolean supports(Class<?> aClass) {
    return (User.class.isAssignableFrom(aClass));
  }

  @Override
  public void validate(Object target, Errors errors) {
    User user = (User)target;

//    if (Objects.nonNull(target) && StringUtils.isBlank(user.getName())) {
//      errors.rejectValue("name", "field.required");
//    }
    ValidationUtils.rejectIfEmpty(errors, "name", "field.required");

    if (user.getAge() < 0) {
      errors.rejectValue("age", "field.min", new Object[] {0}, null);
    }
  }
}
