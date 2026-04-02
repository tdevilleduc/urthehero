package com.tdevilleduc.urthehero.back.model

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import jakarta.persistence.*


@Entity
@Table(name = "USERS")
open class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "\${swagger.model.user.param.storyId}")
        open var userId: Int?,
        private var username: String = "",
        private var password: String = "",
        @Column(nullable = false)
        private var role: String = "ROLE_USER"
) : Serializable, UserDetails {

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role))
    }

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    open fun getRole(): String {
        return role
    }

    open fun setUsername(username: String) {
        this.username = username
    }

    open fun setPassword(password: String) {
        this.password = password
    }

    open fun setRole(role: String) {
        this.role = role
    }
}