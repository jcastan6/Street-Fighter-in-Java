package GameFiles;

import java.awt.*;

public interface Collisionable {
    void handleCollision(Collisionable object);
    Hitbox getHitbox();
    void hit(Rectangle intersection, int i, int damage);
}
