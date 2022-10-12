/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package renderer;

import VO.perusahaanVO;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

/**
 *
 * @author helloWorld2
 */
public class ShowPerusahaanNamaRenderer implements ComboitemRenderer<perusahaanVO> {

    @Override
    public void render(Comboitem comboitem, perusahaanVO t, int i) throws Exception {
//        perusahaanVO vo = (perusahaanVO) t;
        System.out.println("vo = " + t);
        if (t != null) {
            comboitem.setValue(t.getId_perusahaan());
            comboitem.setLabel(t.getNama_perusahaan());
            System.out.println("t.getId_perusahaan =" + t.getId_perusahaan());
        } else {
            comboitem.setValue(null);
            comboitem.setLabel("Kosong");
        }
    }
}
