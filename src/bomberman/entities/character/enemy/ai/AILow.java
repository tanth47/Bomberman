/**
 * @author Kyoraku
 */

package bomberman.entities.character.enemy.ai;

public class AILow extends AI {
    /**
     * override function to calculate direction.
     * @return int direction
     */
    @Override
    public int calculateDirection() {
        return random.nextInt(4);
    }
}