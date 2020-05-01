package com.tdevilleduc.urthehero.back.constant

object ApplicationConstants {
    const val CHECK_DICE_PARAMETER_MANDATORY: String = "The dice parameter is mandatory !"
    const val CHECK_COUNT_PARAMETER_MANDATORY: String = "The count parameter is mandatory !"
    const val CHECK_PAGEID_PARAMETER_MANDATORY: String = "The pageId parameter is mandatory !"
    const val CHECK_USERID_PARAMETER_MANDATORY: String = "The userId parameter is mandatory !"
    const val CHECK_STORYID_PARAMETER_MANDATORY: String = "The storyId parameter is mandatory !"
    const val CHECK_ENEMYID_PARAMETER_MANDATORY: String = "The ennemyId parameter is mandatory !"

    const val ERROR_MESSAGE_PAGE_DOESNOT_EXIST: String = "La page avec l'id {} n'existe pas"
    const val ERROR_MESSAGE_USER_DOESNOT_EXIST: String = "Le user avec l'id {} n'existe pas"
    const val ERROR_MESSAGE_USER_USERNAME_DOESNOT_EXIST: String = "Le user avec le username {} n'existe pas"
    const val ERROR_MESSAGE_STORY_DOESNOT_EXIST: String = "L'histoire avec l'id {} n'existe pas"
    const val ERROR_MESSAGE_ENEMY_ID_DOESNOT_EXIST: String = "L'ennemi avec l'id {} n'existe pas"

    const val ERROR_MESSAGE_ENEMY_DOESNOT_EXIST: String = "Aucun ennemi n'a été trouvé"

    const val ERROR_MESSAGE_USER_USERID_ALREADY_EXISTS : String = "Un utilisateur avec l'identifiant {} existe déjà. Il ne peut être créé"
    const val ERROR_MESSAGE_PAGE_PAGEID_ALREADY_EXISTS : String = "Une page avec l'identifiant {} existe déjà. Elle ne peut être créée"
    const val ERROR_MESSAGE_ENEMY_ID_ALREADY_EXISTS : String = "Un ennemi avec l'identifiant {} existe déjà. Il ne peut être créé"

    const val ERROR_MESSAGE_USER_USERID_CANNOT_BE_NULL: String = "L'identifiant de l'utilisateur passé en paramètre ne peut pas être null"
    const val ERROR_MESSAGE_PAGE_PAGEID_CANNOT_BE_NULL: String = "L'identifiant de la page passée en paramètre ne peut pas être null"
    const val ERROR_MESSAGE_ENEMY_ID_CANNOT_BE_NULL: String = "L'identifiant de l'ennemi passée en paramètre ne peut pas être null"

    const val CONTROLLER_CALL_LOG: String = "call: {}"
}