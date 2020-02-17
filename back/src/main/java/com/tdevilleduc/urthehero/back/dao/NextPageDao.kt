package com.tdevilleduc.urthehero.back.dao

import com.tdevilleduc.urthehero.back.model.NextPage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NextPageDao : JpaRepository<NextPage, Int?> {
    fun findByPageId(pageId: Int?): MutableList<NextPage>
}