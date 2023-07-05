package nl.digitalinvestigation.filecrawl.validation;

import nl.digitalinvestigation.filecrawl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, Object> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueEmail uniqueEmail) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository == null || userRepository.findByEmail((String) o) == null;
    }
}
