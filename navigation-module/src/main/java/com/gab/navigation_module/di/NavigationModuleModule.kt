package com.gab.navigation_module.di

import com.gab.navigation_module.holder.NavigationHolder
import com.gab.navigation_module.holder.NavigationHolderImpl
import dagger.Binds
import dagger.Module

@Module
public interface NavigationModuleModule {
    @Binds
    @NavigationModuleScope
    public fun bindNavigationHolder(impl: NavigationHolderImpl): NavigationHolder
}