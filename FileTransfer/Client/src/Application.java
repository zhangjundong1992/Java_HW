public class Application {
    public static void main(String[] args) {
        ClientView view = new ClientView();
        Controller controller = new Controller();
        view.setDelegate(controller);
    }
}
