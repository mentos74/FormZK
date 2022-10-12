
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author imam-pc
 */
public class LoginController extends GenericForwardComposer {

    private Textbox username, password;
    private Label message;

    public void onClick$login() {
        if (!username.getValue().trim().equals("") && !password.getValue().trim().equals("")
                && username.getValue().equals("admin") && password.getValue().equals("admin")) {
            //set session to browser
            Session sess = Sessions.getCurrent();
            sess.setAttribute("userCredential", "user");
//            alert("Login berhasil!");
            Executions.sendRedirect("ShowListCustomer.zul");
        } else {
//            message.setValue("Login Incorrect");
            alert("Incorrect Login!");
        }
    }

    public void onOK$username() {
        onClick$login();
    }

    public void onOK$password() {
        onClick$login();
    }

}
