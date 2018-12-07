package render;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import character.Character;
import javafx.scene.canvas.GraphicsContext;
import main.MainApplication;

public class Map {

	private static final int MAP_WIDTH = 100;
	private static final int MAP_HEIGHT = 100;
	private Character player;
	private Tile[][] tiles;

	public Map(Character player) {
		this.player = player;

		tiles = new Tile[MAP_HEIGHT][MAP_WIDTH];
		try {
			Scanner input = new Scanner(new File(ClassLoader.getSystemResource("map1").getFile()));
			for (int i = 0; i < MAP_HEIGHT; i++) {
				for (int j = 0; j < MAP_WIDTH; j++) {
					//tiles[i][j] = new Tile(0);
					if (input.hasNextInt()) {
						tiles[i][j] = new Tile(input.nextInt());
					}
				}
			}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open the map file.");
			e.printStackTrace();
			for (int i = 0; i < MAP_HEIGHT; i++) {
				for (int j = 0; j < MAP_WIDTH; j++) {
					tiles[i][j] = new Tile(0);
				}
			}
		}
	}

	public void render(GraphicsContext gc) {
		int startRenderX = (MainApplication.SCREEN_WIDTH / 2) - (int) player.getPosition().getX();
		int startRenderY = (MainApplication.SCREEN_HEIGHT / 2) - (int) player.getPosition().getY();

		for (int i = 0; i < MAP_HEIGHT; i++) {
			for (int j = 0; j < MAP_WIDTH; j++) {
				try {
					tiles[i][j].render(gc, startRenderX + j * Tile.TILE_SIZE, startRenderY + i * Tile.TILE_SIZE);
				} catch (Exception e) {
					// Tile is rendered out of the screen, so do nothing here.
				}
			}
		}
	}
}
