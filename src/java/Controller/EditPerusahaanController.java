/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DataAccessObject;
import DAO.DataAccessObjectPerusahaan;
import VO.perusahaanVO;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author helloWorld2
 */
public class EditPerusahaanController extends GenericForwardComposer<Component> {

    Textbox txt_nmp, txt_alamat, txt_notelpon, txt_bdg;
    Intbox txt_idperusahaan;
    Button buttonAdd, buttonClose;
    Window inputPerusahaan;
    perusahaanVO vo;
    ShowPerusahaanController parent;

    @Override
    public void doAfterCompose(Component comp) throws Exception {

        try {
            super.doAfterCompose(comp);
            Map map = Executions.getCurrent().getArg();
            vo = (perusahaanVO) map.get("obj");
            parent = (ShowPerusahaanController) map.get("parent");

            txt_idperusahaan.setValue(vo.getId_perusahaan());
            txt_alamat.setValue(vo.getAlamat_perusahaan());
            txt_notelpon.setValue(vo.getNo_telpon());
            txt_bdg.setValue(vo.getBidang_perusahaan());
            txt_nmp.setValue(vo.getNama_perusahaan());
        } catch (Exception e) {
            e.printStackTrace();
        }
        inputPerusahaan.doModal();
    }

    public void onClick$buttonUpdate() {
        DataAccessObjectPerusahaan.getInstance().updateData(txt_idperusahaan.getValue(), txt_nmp.getValue(), txt_alamat.getValue(), txt_notelpon.getValue(), txt_bdg.getValue());
        if (parent != null) {
            parent.prepareList();
        }
        inputPerusahaan.onClose();
    }

    public void onClick$buttonClose() {
        inputPerusahaan.onClose();
    }
}
