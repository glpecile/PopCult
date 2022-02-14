package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ModeratorService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.auth.JwtTokenUtil;
import ar.edu.itba.paw.webapp.dto.input.UserAuthDto;
import ar.edu.itba.paw.webapp.exceptions.EmptyBodyException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("authenticate")
@Component
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

    @Autowired
    private ModeratorService moderatorService;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateController.class);

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response authenticate(@Valid UserAuthDto userAuthDto) {
        if (userAuthDto == null) {
            throw new EmptyBodyException();
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthDto.getUsername(), userAuthDto.getPassword())
        );

        User user = userService.getByUsername(authentication.getName()).orElseThrow(UserNotFoundException::new);
        LOGGER.info("POST /authenticate: User {} authenticated", user.getUsername());
        return Response.noContent()
                .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.createToken(user))
                .build();
    }

}
