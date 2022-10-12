/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DataAccessObject;
import DAO.DataAccessObjectPerusahaan;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import renderer.ShowPerusahaanNamaRenderer;
import util.UIUtil;

/**
 *
 * @author helloWorld2
 */
public class AddCustomerController extends GenericForwardComposer<Component> {

    Textbox txt_nmd, txt_nmb, txt_email, txt_alamat, txt_noTelp;
    Datebox txt_tanggalLahir;
    Intbox txt_id, txt_id_pt;
    Combobox comboboxNamaPerusahaan;
    Listbox listboxNamaPerusahaan;
//    Datebox txt_tanggalLahir;
    Button buttonAdd, buttonClose;
    Window inputCustomer;
    ShowCustomerController parent;
//    
//    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");  
//       String x = formatter.format(txt_tanggalLahir);

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        Map map = Executions.getCurrent().getArg();
        parent = (ShowCustomerController) map.get("parent");
        prepareComboPerusahaan();
        inputCustomer.doModal();

    }

    public void onClick$buttonAdd() {
        try {

            System.out.println("txt_tanggalLahir.getValue() - " + txt_tanggalLahir.getValue());
//            Date date = txt_tanggalLahir.getValue();
//            System.out.println("date - " + date);
            Date date = txt_tanggalLahir.getValue();
            Timestamp ts = new Timestamp(date.getTime());
//            String temp = comboboxNamaPerusahaan.getValue();
            System.out.println(ts);

            System.out.println("UIUtil.getComboValue(comboboxNamaPerusahaan) =" + UIUtil.getComboValue(comboboxNamaPerusahaan));
            String temp = UIUtil.getComboValue(comboboxNamaPerusahaan);
            DataAccessObject.getInstance().insertData(txt_nmd.getValue(), txt_nmb.getValue(), txt_email.getValue(), txt_alamat.getValue(), txt_noTelp.getValue(), ts, Integer.valueOf(temp));

            if (parent != null) {
                parent.prepareList();
            }

            inputCustomer.onClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick$buttonClose() {
        inputCustomer.onClose();
    }

    public void prepareComboPerusahaan() {
//        Map map = new HashMap();
        List list = DataAccessObjectPerusahaan.getInstance().selectData();
        System.out.println("list - " + list);
        UIUtil.renderCombobox(comboboxNamaPerusahaan, list, new ShowPerusahaanNamaRenderer());
    }

}
