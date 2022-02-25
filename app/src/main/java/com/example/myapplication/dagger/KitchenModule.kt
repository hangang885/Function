package com.example.myapplication.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class KitchenModule {
    @Provides
    fun provideIsOrder(chef: Chef?, @Named("course1") order: String?): Kitchen {
        return Kitchen(chef!!, order!!)
    }

    @Provides
    @Named("course1")
    fun provideCourse1(): String {
        return "한식"
    }

    @Provides
    @Named("course2")
    fun provideCourse2(): String {
        return "중식"
    }
}