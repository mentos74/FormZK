
import java.util.Map;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;

/**
 *
 * @author imam-pc
 */
public class AuthController implements Initiator {

    @Override
    public void doInit(Page page, Map<String, Object> args) throws Exception {
        String name = (String) args.get("name");
        String cre = (String) Sessions.getCurrent().getAttribute("userCredential");
        if (cre == null) {
            if (!name.equals("Login")) {
                Executions.sendRedirect("/Login" + ".zul");
            }
        } else {
            if (!name.equals("ShowListCustomer")) {
                Executions.sendRedirect("/ShowListCustomer" + ".zul");
            }
        }
    }
}
