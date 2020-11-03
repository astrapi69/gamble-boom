package io.github.astrapi69.emailsystem.exceptionhandling;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import de.alpharogroup.spring.exceptionhandling.ControllerExceptionHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@ControllerAdvice(annotations = RestController.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationExceptionHandler extends ControllerExceptionHandler
{

	public ApplicationExceptionHandler(MessageSource messageSource)
	{
		super(messageSource);
	}

}
