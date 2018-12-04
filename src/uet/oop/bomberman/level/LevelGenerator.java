package uet.oop.bomberman.level;

import java.util.Random;

public class LevelGenerator {
    public static char[][] generate() {
        char[][] map = new char[13][31];
        String[] template = new String[13];
        template[0] = "###############################";
        template[1] = "#p                            #";
        template[2] = "# # # # # # # # # # # # # # # #";
        template[3] = "#                             #";
        template[4] = "# # # # # # # # # # # # # # # #";
        template[5] = "#                             #";
        template[6] = "# # # # # # # # # # # # # # # #";
        template[7] = "#                             #";
        template[8] = "# # # # # # # # # # # # # # # #";
        template[9] = "#                             #";
        template[10] = "# # # # # # # # # # # # # # # #";
        template[11] = "#                             #";
        template[12] = "###############################";
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                map[i][j] = template[i].charAt(j);
            }
        }
        Random rand = new Random();
        // Generate 6 enemies
        for (int i = 1; i <= 6; i++) {
            int enemy = rand.nextInt(5) + 1; // Random enemy type
            int x = rand.nextInt(25) + 5, y = rand.nextInt(10) + 2;
            while (map[y][x] != ' ') {
                x = rand.nextInt(25) + 5;
                y = rand.nextInt(10) + 2;
            }
            map[y][x] = Integer.toString(enemy).charAt(0);
        }
        // Generate 1-2 power-ups
        for (int i = 1; i <= (rand.nextInt(2) + 1); i++) {
            int powerup = rand.nextInt(3); // Random power-up type
            int x = rand.nextInt(25) + 5, y = rand.nextInt(10) + 2;
            while (map[y][x] != ' ') {
                x = rand.nextInt(25) + 5;
                y = rand.nextInt(10) + 2;
            }
            switch (powerup) {
                case 0 :    map[y][x] = 'b'; // Bomb item
                            break;
                case 1 :    map[y][x] = 'f'; // Flame item
                            break;
                case 2 :    map[y][x] = 's'; // Speed item
                            break;
            }
        }
        // Generate 1-2 portals
        for (int i = 1; i <= (rand.nextInt(2) + 1); i++) {
            int x = rand.nextInt(25) + 5, y = rand.nextInt(10) + 2;
            while (map[y][x] != ' ' && !(y == 1 && x == 2) && !(y == 2 && x == 1)) {
                x = rand.nextInt(25) + 5;
                y = rand.nextInt(10) + 2;
            }
            map[y][x] = 'x';
        }
        // Generate 50-60 bricks
        for (int i = 1; i <= (rand.nextInt(10) + 51); i++) {
            int x = rand.nextInt(31), y = rand.nextInt(13);
            while (map[y][x] != ' ' && !(y == 1 && x == 2) && !(y == 2 && x == 1)) {
                x = rand.nextInt(31);
                y = rand.nextInt(13);
            }
            map[y][x] = '*';
        }
        return map;
    }
}
