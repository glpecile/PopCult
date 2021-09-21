package ar.edu.itba.paw.webapp.form.constraints;

import ar.edu.itba.paw.webapp.form.annotations.Image;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageValidatorConstraint implements ConstraintValidator<Image, MultipartFile> {
    private static final long MAX_SIZE = 1024*1024;//1MB
    private static final String ACCEPTED_MIME_TYPES = "image/";


    @Override
    public void initialize(Image image) {

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return !multipartFile.isEmpty() && multipartFile.getSize() < MAX_SIZE && multipartFile.getContentType().contains(ACCEPTED_MIME_TYPES);
    }
}
