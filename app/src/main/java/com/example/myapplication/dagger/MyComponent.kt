package com.example.myapplication.dagger

import dagger.Component
import com.example.myapplication.MainActivity


@Component(modules = [ChefModule::class, KitchenModule::class])
interface MyComponent {
    fun inject(activity: Dagger2Activity?)
}