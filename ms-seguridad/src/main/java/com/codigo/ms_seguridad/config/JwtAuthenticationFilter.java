package com.codigo.ms_seguridad.config;

import com.codigo.ms_seguridad.service.JwtService;
import com.codigo.ms_seguridad.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
//filtra cada solicitud entrante
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String tokenExtraidoHeader = request.getHeader("Authorization");
        final String tokenLimpio;
        final String usserName;


        if (!StringUtils.hasText(tokenExtraidoHeader) ||
                !StringUtils.startsWithIgnoreCase(tokenExtraidoHeader, "Bearer ")){
            filterChain.doFilter(request, response);
            return ;
        }


        //limpiando token
        tokenLimpio = tokenExtraidoHeader.substring(7);

        usserName = jwtService.extractUsername(tokenLimpio);

        //validamos que no esta autenticado
        if(Objects.nonNull(usserName)
                && SecurityContextHolder.getContext().getAuthentication() == null){

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            UserDetails userDetails = usuarioService.userDetailsService().loadUserByUsername(usserName);

            //validamos el token

            if(jwtService.validateToken(tokenLimpio, userDetails) &&
                    !jwtService.isRefreshToken(tokenLimpio)){
                //objeto: usuario autenticado
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


                //agregamos detalles adicionales sobre el obj autenticado
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        }

        //continuax
        filterChain.doFilter(request,response);
    }
}
