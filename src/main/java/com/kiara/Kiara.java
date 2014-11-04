package com.kiara;

import com.kiara.impl.ContextImpl;

public class Kiara {
    public static Context createContext() {
        return new ContextImpl();
    }
}
