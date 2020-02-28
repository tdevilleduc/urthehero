package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.model.NextPage

interface INextPageService {
    fun findByPageId(pageId: Int): MutableList<NextPage>
}