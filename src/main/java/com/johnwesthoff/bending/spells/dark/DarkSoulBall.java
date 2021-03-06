
package com.johnwesthoff.bending.spells.dark;

import com.johnwesthoff.bending.Client;
import com.johnwesthoff.bending.Constants;
import com.johnwesthoff.bending.Server;
import com.johnwesthoff.bending.entity.SoulDrainEntity;
import com.johnwesthoff.bending.logic.World;
import com.johnwesthoff.bending.spells.Spell;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.johnwesthoff.bending.util.network.ResourceLoader.loadIcon;

public class DarkSoulBall extends Spell {
    public DarkSoulBall() {
        ID = Server.DARKNESS;
        subID = 1;
        locked = true;
        unlockXP = 2500;
        try {
            icon = loadIcon("https://west-it.webs.com/spells/shadowBall.png");
        } catch (Exception ex) {
            Logger.getLogger(Spell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void getAction(Client app) {
        X = app.world.x;
        Y = app.world.y - Constants.HEAD;
        mx = app.world.viewX;
        my = app.world.mouseY - app.world.viewY;
        double direction = Constants.FULL_ANGLE - Client.pointDir(app.world.x - app.world.viewX,
                app.world.y - Constants.HEAD - app.world.viewY, app.world.mouseX, app.world.mouseY);
        // direction+=180;
        mx = ((int) (Client.lengthdir_x(8, direction)));
        my = ((int) (Client.lengthdir_y(8, direction)));
        maker = ID;
        getMessage(app.out);
    }

    @Override
    public int getCost() {
        return 400;
    }

    @Override
    public String getName() {
        return "Soul Sucker";
    }

    @Override
    public void getPassiveAction(Client app) {
    }

    @Override
    public String getTip() {
        return "<html>A basic darkness spell<br>Moderate-High Energy Cost<br>Steal the health of your foe<br>Unaffected by gravity</html>";
    }

    @Override
    public void getActionNetwork(World world, int px, int py, int mx, int my, int pid, int eid, ByteBuffer buf) {
        world.entityList.add(new SoulDrainEntity(px, py, mx, my, pid).setID(eid));
    }
}

