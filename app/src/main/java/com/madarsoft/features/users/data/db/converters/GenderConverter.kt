package com.madarsoft.features.users.data.db.converters

import androidx.room.TypeConverter
import com.madarsoft.features.users.domain.entities.UserEntity

object GenderConverter {
    @TypeConverter
    fun fromResponsivePOJO(value: UserEntity.Gender): String {
        return value.name
    }

    @TypeConverter
    fun toResponsivePOJO(value: String): UserEntity.Gender {
        return UserEntity.Gender.valueOf(value)
    }

}
