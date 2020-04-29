package com.tdevilleduc.urthehero.back.model

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name = "USERS")
open class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "\${swagger.model.user.param.storyId}")
        var userId: Int?,
        private var username: String = "",
        private var password: String = ""
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

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return emptyList()
    }

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun setPassword(password: String) {
        this.password = password
    }
}