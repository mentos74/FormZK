/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package renderer;

import DAO.DataAccessObject;
import DAO.DataAccessObjectPerusahaan;
import VO.customerVO;
import VO.perusahaanVO;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author helloWorld2
 */
public class ShowCustomerRenderer implements ListitemRenderer {

    @Override
    public void render(Listitem lstm, Object t, int i) throws Exception {
        customerVO vo = (customerVO) t;
        Listcell cell = new Listcell();
        System.out.println("ini adalah cell render =" + cell);
        String nameCompany = DataAccessObject.getInstance().selectNameCompany(vo.getId_perusahaan());

        String z = Integer.toString(vo.getId());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String x = formatter.format(vo.getTanggalLahir());

        cell.setLabel(z);
        cell.setParent(lstm);

        cell = new Listcell(vo.getNamaDepan());
        cell.setParent(lstm);

        cell = new Listcell(vo.getNamaBelakang());
        cell.setParent(lstm);

        cell = new Listcell(vo.getEmail());
        cell.setParent(lstm);

        cell = new Listcell(vo.getAlamat());
        cell.setParent(lstm);

        cell = new Listcell(vo.getNoTelpon());
        cell.setParent(lstm);

        cell = new Listcell(x);
        cell.setParent(lstm);

        System.out.println("nameCompany =" + nameCompany);
        cell = new Listcell(nameCompany);
        cell.setParent(lstm);
        
        lstm.setAttribute("data", vo);
    }

    public static void main(String[] args) {
//        System.out.println(renderer.);
    }

}
