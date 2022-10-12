/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DataAccessObject;
import DAO.DataAccessObjectPerusahaan;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author helloWorld2
 */
public class AddPerusahaanController extends GenericForwardComposer<Component> {

    Textbox txt_nmp, txt_alamat, txt_notelpon, txt_bdg;

    Button buttonAdd, buttonClose;
    Window inputPerusahaan;
    ShowPerusahaanController parent;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        Map map = Executions.getCurrent().getArg();
        parent = (ShowPerusahaanController) map.get("parent");
        inputPerusahaan.doModal();

    }

    public void onClick$buttonAdd() {
        try {
            DataAccessObjectPerusahaan.getInstance().insertData(txt_nmp.getValue(), txt_alamat.getValue(), txt_notelpon.getValue(), txt_bdg.getValue());
            if (parent != null) {
                parent.prepareList();
            }

            inputPerusahaan.onClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick$buttonClose() {
        inputPerusahaan.onClose();
    }
}
