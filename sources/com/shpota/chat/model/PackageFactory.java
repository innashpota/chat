package com.shpota.chat.model;

import com.shpota.chat.model.jdbc.JdbcChatRepository;
import com.shpota.chat.model.strategies.Strategy;

import java.util.Map;

public class PackageFactory {
    public Strategy getStrategy(
            Class packageClass,
            JdbcChatRepository chatRepository
    ) {
        PackageDispatcher dispatcher = PackageDispatcher.getInstance(chatRepository);
        Map<Class, Strategy> map = dispatcher.map;

        return map.get(packageClass);
    }
}
