package com.baobab.pp.global.security.jwt.provider

import com.baobab.pp.domain.user.domain.entity.UserEntity
import com.baobab.pp.global.security.jwt.config.JwtProperties
import com.baobab.pp.global.error.CustomException
import com.baobab.pp.global.security.jwt.details.JwtUserDetailsService
import com.baobab.pp.global.security.jwt.dto.Jwt
import com.baobab.pp.global.security.jwt.enums.JwtType
import com.baobab.pp.global.security.jwt.error.JwtError
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val jwtUserDetailsService: JwtUserDetailsService
) {
    private val key = SecretKeySpec(
        jwtProperties.secret.toByteArray(),
        Jwts.SIG.HS256.key().build().algorithm
    )

    fun generateToken(user: UserEntity): Jwt {
        val now = Date()

        val accessToken = Jwts.builder()
            .header()
            .type(JwtType.ACCESS.name)
            .and()
            .claim("role", user.role)
            .signWith(key)
            .issuedAt(now)
            .issuer(jwtProperties.issuer)
            .subject(user.username)
            .expiration(now.apply { time += jwtProperties.accessTokenExpiration })
            .compact()

        val refreshToken = Jwts.builder()
            .header()
            .type(JwtType.REFRESH.name)
            .and()
            .signWith(key)
            .issuedAt(now)
            .issuer(jwtProperties.issuer)
            .subject(user.username)
            .expiration(now.apply { time += jwtProperties.refreshTokenExpiration })
            .compact()

        return Jwt(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun validateToken(token: String) {
        try {
            Jwts.parser().verifyWith(key).requireIssuer(jwtProperties.issuer).build().parseSignedClaims(token)
        } catch (e: ExpiredJwtException) {
            throw CustomException(JwtError.EXPIRED_TOKEN)
        } catch (e: UnsupportedJwtException) {
            throw CustomException(JwtError.UNSUPPORTED_TOKEN)
        } catch (e: SecurityException) {
            throw CustomException(JwtError.INVALID_TOKEN)
        } catch (e: MalformedJwtException) {
            throw CustomException(JwtError.INVALID_TOKEN)
        } catch (e: IllegalArgumentException) {
            throw CustomException(JwtError.INVALID_TOKEN)
        }
    }

    fun extractToken(request: HttpServletRequest) = request.getHeader("Authorization")?.removePrefix("Bearer ")
    fun extractToken(accessor: StompHeaderAccessor) =
        accessor.getFirstNativeHeader("Authorization")?.removePrefix("Bearer ")

    fun getType(token: String) = JwtType.valueOf(getClaims(token).header.type)

    fun getUsername(token: String): String = getClaims(token).payload.subject

    fun getAuthentication(token: String): Authentication {
        val user = jwtUserDetailsService.loadUserByUsername(
            Jwts.parser().verifyWith(key).requireIssuer(jwtProperties.issuer).build()
                .parseSignedClaims(token).payload.subject
        )

        return UsernamePasswordAuthenticationToken(user, "", user.authorities)
    }

    private fun getClaims(token: String) =
        Jwts.parser().verifyWith(key).requireIssuer(jwtProperties.issuer).build().parseSignedClaims(token)
}