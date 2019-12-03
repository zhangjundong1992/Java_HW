import utilities.Observer;
import utilities.ResCode;

public class Controller implements Observer {
    private TransView tsView;
    private AccountModel accModel;

    private Controller() {
        tsView = new TransView();
        tsView.setObserver(this);
        accModel = new AccountModel();
    }

    @Override
    public void update(Object[] arg) {
        try {
            String user_out = (String) arg[0];
            String user_in = (String) arg[1];
            int count = Integer.parseInt((String) arg[2]);
            ResCode res = accModel.transferMoney(user_out, user_in, count);
            tsView.ShowResult(res);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Controller();
    }
}
