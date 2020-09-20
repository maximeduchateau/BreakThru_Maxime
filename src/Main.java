public class Main {
    private Game game;
    private GUI gui;

    //constructor
    public Main() {


    }

    public static void main(String[] args) {
        Game game = new Game();
        GUI gui = new GUI(game);

        game.attach(gui);
    }
}
