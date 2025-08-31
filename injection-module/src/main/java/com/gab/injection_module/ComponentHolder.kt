package com.gab.injection_module

public interface ComponentHolder<C: BaseApi, D: BaseDependencies> {

    public fun init(dependencies: D)
    
    public fun get(): C

    public fun reset()

}

public interface BaseApi

public interface BaseDependencies
