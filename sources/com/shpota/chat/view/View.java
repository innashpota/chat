package com.shpota.chat.view;

import com.shpota.chat.model.net.ClientModel;
import com.shpota.chat.model.packages.Package;

import java.awt.*;
import java.net.URL;

import static java.awt.Toolkit.getDefaultToolkit;

public abstract class View {
    private static final String IMAGE_PATH = "/images/icon.png";
    public ClientModel model;

    View(ClientModel model) {
        this.model = model;
        model.addView(this);
    }

    Image getIconImage() {
        URL url = getClass().getResource(IMAGE_PATH);
        return getDefaultToolkit().getImage(url);
    }

    public abstract void show();

    public abstract void hide();

    public abstract void onPackageReceived(Package pkg);
}
