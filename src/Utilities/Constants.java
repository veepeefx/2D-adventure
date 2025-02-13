package Utilities;

public class Constants {

    public static class Direction {

        public static final int RIGHT = 0;
        public static final int LEFT = 1;
        public static final int DOWN = 2;
        public static final int UP = 3;
    }

    public static class ActionTypes {

        public static final int IDLE = 1;
        public static final int WALK = 2;


        public static int get_player_sprite_amount(int action){
/*
            if (action == IDLE_RIGHT || action == IDLE_LEFT || action == IDLE_DOWN ||action == IDLE_UP){
                return 4;
            } else if (action == WALK_RIGHT || action == WALK_LEFT || action == WALK_DOWN ||action == WALK_UP){
                return 8;
            } else {
                return 4;
            }
<
 */
            if (action == IDLE) return 4;
            else if (action == WALK) return 8;
            else return -1;
        }
    }

    // GAME STATUS
    public static class GameStatus {

        public static final int MENU = 0;
        public static final int PLAY = 1;
        public static final int PAUSE = 2;
    }

    public static class GameConstants {

        public static final int TILE_PX_SIZE = 16;
        public static final int SCALE = 4;
        public static final int SCREEN_ROWS = 12;
        public static final int SCREEN_COLUMNS = 16;

    }
}
