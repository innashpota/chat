package com.shpota.chat.view;

import com.shpota.chat.model.net.ClientModel;
import com.shpota.chat.model.packages.Package;

public abstract class View {
    public static final String IMAGE_PATH = "/images/icon.png";
    public ClientModel model;

    View(ClientModel model) {
        this.model = model;
        model.addView(this);
    }

    public abstract void onPackageReceived(Package pkg);
}
