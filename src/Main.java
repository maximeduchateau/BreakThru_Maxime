public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        GUI gui = new GUI(game);
        AI ai = new AI(game);
        game.attach(gui);
        game.attach(ai);

        double coinFlip=Math.random();

        if (coinFlip>0.5){gui.setTeam(false); ai.setTeam(true);
            System.out.println("ai plays silver (true)");}
        else{gui.setTeam(true); ai.setTeam(false);
            System.out.println("ai plays silver (false)");}
        game.start();
    }
}
