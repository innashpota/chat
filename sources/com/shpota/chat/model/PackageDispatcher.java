package com.shpota.chat.model;

import com.shpota.chat.model.packages.AddMessageClientPackage;
import com.shpota.chat.model.packages.LoginClientPackage;
import com.shpota.chat.model.packages.RegistrationClientPackage;
import com.shpota.chat.model.packages.RequestMessagesClientPackage;
import com.shpota.chat.model.strategies.*;

import java.util.HashMap;
import java.util.Map;

public class PackageDispatcher {
    private final Map<Class, Strategy> map;

    private PackageDispatcher(Map<Class, Strategy> map) {
        this.map = map;
    }

    public static PackageDispatcher construct(ChatRepository chatRepository) {
        Map<Class, Strategy> dispatch = new HashMap<>();
        dispatch.put(
                RegistrationClientPackage.class,
                new RegistrationStrategy(chatRepository)
        );
        dispatch.put(
                LoginClientPackage.class,
                new LoginStrategy(chatRepository)
        );
        dispatch.put(
                RequestMessagesClientPackage.class,
                new RequestMessagesStrategy(chatRepository)
        );
        dispatch.put(
                AddMessageClientPackage.class,
                new AddMessageStrategy(chatRepository)
        );
        return new PackageDispatcher(dispatch);
    }

    public Strategy getStrategy(Class packageClass) {
        return map.get(packageClass);
    }
}
