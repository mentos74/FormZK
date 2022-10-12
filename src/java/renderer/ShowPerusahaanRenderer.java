/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package renderer;

import VO.customerVO;
import VO.perusahaanVO;
import java.text.SimpleDateFormat;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author helloWorld2
 */
public class ShowPerusahaanRenderer implements ListitemRenderer {

    @Override
    public void render(Listitem lstm, Object t, int i) throws Exception {
        perusahaanVO vo = (perusahaanVO) t;
        Listcell cell = new Listcell();

        String z = Integer.toString(vo.getId_perusahaan());

        cell.setLabel(z);
        cell.setParent(lstm);

        cell = new Listcell(vo.getNama_perusahaan());
        cell.setParent(lstm);

        cell = new Listcell(vo.getAlamat_perusahaan());
        cell.setParent(lstm);

        cell = new Listcell(vo.getNo_telpon());
        cell.setParent(lstm);

        cell = new Listcell(vo.getBidang_perusahaan());
        cell.setParent(lstm);

        lstm.setAttribute("data", vo);
    }

}
