package com.codigo.ms_seguridad.service.Impl;

import com.codigo.ms_seguridad.aggregates.constants.Constants;
import com.codigo.ms_seguridad.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
public class JwtServiceImpl implements JwtService {




    @Value("${key.signature}")
    private String keySignature;


    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }



    @Override
    public String generateToken(UserDetails userDetails) {
         log.info("Ejecutando - generateToken - ");


        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(addClaim(userDetails))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) //fecha en la que se genera el token
                .setExpiration(new Date(System.currentTimeMillis()+1200000))
                .claim("type", Constants.ACCESS)
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact(); //el compact lo vuelve string
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {

        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



    //token con mayor tiempo
    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1200000000))
                .claim("type", Constants.REFRESH)
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public boolean isRefreshToken(String token) {
        Claims claims = extractAllClaims(token);
        String tokenType = claims.get("type",String.class);
        return Constants.REFRESH.equalsIgnoreCase(tokenType);
    }



    /*METODOS DE APOYO*/

    //--- Llave para firmar los Tokens - devuelve clave en su forma natural (HS512)
    private Key getSignKey(){
        byte [] key = Decoders.BASE64.decode(keySignature);

        return Keys.hmacShaKeyFor(key);
    }

    //---Extraccion de payload(claims)
    private Claims extractAllClaims(String token){

       /* Jwts.parserBuilder() --> divide en sus 3 partes el token recibido*/
        /*.parseClaimsJws(token).getBody() ---> indicamos que deseamos obtener del token */

        return Jwts.parserBuilder().setSigningKey(getSignKey()).build()
                .parseClaimsJws(token).getBody();
    }

    //-----Obtenemos un elemento (atributo) del payload(claims) - con un metodo generico
    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction){

        return claimsTFunction.apply(extractAllClaims(token));
    }

    //----------Validar si Token esta expirado
    private boolean isTokenExpired(String token){
        /*.before(new Date() : compara si la fecha de expiracion es menor a la fecha actual, de ser asi marca true y false si aun es valido*/
        return extractClaim(token,Claims ::getExpiration).before(new Date());

    }


    /** clave:string - valor:objeto(boolean,string,int)  **/
    private Map<String, Object> addClaim(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.CLAVE_AccountNonLocked,userDetails.isAccountNonLocked());
        claims.put(Constants.CLAVE_AccountNonExpired,userDetails.isAccountNonExpired());
        claims.put(Constants.CLAVE_CredentialsNonExpired,userDetails.isCredentialsNonExpired());
        claims.put(Constants.CLAVE_Enabled,userDetails.isEnabled());
        claims.put("TipoUsuario",userDetails.getAuthorities()
                .stream().map(rol -> rol.getAuthority())
                .findFirst()
                .orElse("NOT_VALUE"));

        return claims;
    }
}
