package com.example.myapplication.dagger

import dagger.Module
import dagger.Provides

@Module
class ChefModule {
    @Provides
    fun provideChef(): Chef {
        return Chef("Black", "Jin")
    }
}