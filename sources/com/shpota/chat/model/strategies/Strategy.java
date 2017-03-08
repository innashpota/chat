package com.shpota.chat.model.strategies;

import com.shpota.chat.model.packages.Package;

public interface Strategy<E extends Package> {
    Package handle(E pkg);
}
