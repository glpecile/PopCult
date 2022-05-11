package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.validation.annotations.Image;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageValidatorConstraint implements ConstraintValidator<Image, FormDataBodyPart> {

    private static final String ACCEPTED_MIME_TYPES = "image/";

    public void initialize(Image constraint) {
    }

    public boolean isValid(FormDataBodyPart image, ConstraintValidatorContext context) {
        return image != null && image.getMediaType().toString().contains(ACCEPTED_MIME_TYPES);
    }

}
