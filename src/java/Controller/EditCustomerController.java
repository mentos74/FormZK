/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DataAccessObject;
import DAO.DataAccessObjectPerusahaan;
import VO.customerVO;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import renderer.ShowPerusahaanNamaRenderer;
import util.UIUtil;

/**
 *
 * @author helloWorld2
 */
public class EditCustomerController extends GenericForwardComposer<Component> {

    Textbox txt_nmd, txt_nmb, txt_email, txt_alamat, txt_noTelp;
    Intbox txt_id, txt_id_pt;
    Button buttonAdd, buttonClose;
    Window inputCustomer;
    Combobox comboboxNamaPerusahaan;
    customerVO vo;
    ShowCustomerController parent;
    Datebox txt_tanggalLahir;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        try {

            super.doAfterCompose(comp);
            Map map = Executions.getCurrent().getArg();
            vo = (customerVO) map.get("obj");
            parent = (ShowCustomerController) map.get("parent");
            System.out.println("vo di edit " + vo);
            txt_id.setValue(vo.getId());
            txt_nmd.setValue(vo.getNamaDepan());
            txt_nmb.setValue(vo.getNamaBelakang());
            txt_email.setValue(vo.getEmail());
            txt_alamat.setValue(vo.getAlamat());
            txt_noTelp.setValue(vo.getNoTelpon());
//            System.out.println("vo.getTanggalLahir() ="+vo.getTanggalLahir());
            txt_tanggalLahir.setValue(vo.getTanggalLahir());
//        comboboxNamaPerusahaan.setValue(DataAccessObject.getInstance().selectNameCompany(vo.getId_perusahaan()));
            comboboxNamaPerusahaan.setValue(DataAccessObject.getInstance().selectNameCompany(vo.getId_perusahaan()));
            prepareComboPerusahaan();

        } catch (Exception e) {
            e.printStackTrace();
        }
        inputCustomer.doModal();
    }

    public void onClick$buttonUpdate() {
        System.out.println("UIUtil.getComboValue(comboboxNamaPerusahaan) =" + UIUtil.getComboValue(comboboxNamaPerusahaan));
        String temp = UIUtil.getComboValue(comboboxNamaPerusahaan);
        DataAccessObject.getInstance().updateData(txt_id.getValue(), txt_nmd.getValue(), txt_nmb.getValue(), txt_email.getValue(), txt_alamat.getValue(), txt_noTelp.getValue(), txt_tanggalLahir.getValue(), Integer.valueOf(temp));
        if (parent != null) {
            parent.prepareList();
        }
        inputCustomer.onClose();
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
