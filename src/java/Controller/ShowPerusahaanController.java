/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DataAccessObject;
import DAO.DataAccessObjectPerusahaan;

import VO.perusahaanVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import renderer.ShowCustomerRenderer;
;

import renderer.ShowPerusahaanRenderer;
import renderer.ShowPerusahaanRenderer;
import renderer.ShowPerusahaanRenderer;
import renderer.ShowPerusahaanRenderer;
import renderer.ShowPerusahaanRenderer;
import renderer.ShowPerusahaanRenderer;
import renderer.ShowPerusahaanRenderer;
import renderer.ShowPerusahaanRenderer;

/**
 *
 * @author helloWorld2
 */


public class ShowPerusahaanController extends GenericForwardComposer<Component> {

    Window onOpen;
    Listbox listboxPerusahaan;
    Combobox comboInput;
    Textbox keywordBox;
    String keywords;
    String comboString;

    public void prepareList() {
        List list = DataAccessObjectPerusahaan.getInstance().selectData();
        listboxPerusahaan.setItemRenderer(new ShowPerusahaanRenderer());
        listboxPerusahaan.setModel(new ListModelList(list));
    }

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        prepareList();
    }

    public void onClick$buttonAdd() {
        Map map = new HashMap();
        map.put("parent", this);
        Executions.createComponents("AddPerusahaan.zul", null, map);
    }

    public void onClick$buttonUpdate() {
        if (listboxPerusahaan.getSelectedItem() != null) {
            Object t = listboxPerusahaan.getSelectedItem().getAttribute("data");

            perusahaanVO vo = (perusahaanVO) t;
            vo.setNama_perusahaan(DataAccessObject.getInstance().selectNameCompany(vo.getId_perusahaan()));

            System.out.println("vo edit di show = " + vo);
            Map map = new HashMap();
            map.put("obj", vo);
            map.put("parent", this);
            Executions.createComponents("EditPerusahaan.zul", null, map);
//            alert("Data telah berhasil di update!");
        } else {
            alert("Silahkan pilih data yang ingin di update !");
        }
    }

    public void onClick$buttonDelete() {
        if (listboxPerusahaan.getSelectedItem() != null) {
            Object t = listboxPerusahaan.getSelectedItem().getAttribute("data");
            perusahaanVO vo = (perusahaanVO) t;
            DataAccessObjectPerusahaan.getInstance().deleteData(vo.getId_perusahaan());
            prepareList();
            alert("Data telah berhasil di hapus!");
        } else {
            alert("Silahkan pilih data yang ingin di hapus !");
        }
    }

    public void onClick$buttonLogout() {
        Session sess = Sessions.getCurrent();
        sess.removeAttribute("userCredential");
        Executions.sendRedirect("/Login" + ".zul");
    }

//    public void onSelect$comboId(){
//        if(onOpen.){
//             Executions.sendRedirect("ShowListPerusahaan.zul");
//        }if (onOpen.addEventListener("ShowListCustomer", this)){
//            Executions.sendRedirect("ShowListCustomer.zul");
//        }
//        
//    }
    public void onClick$buttonListCustomer() {
        Executions.sendRedirect("ShowListCustomer.zul");
    }

    public void onChange$comboFormPerusahaan() {
        Executions.sendRedirect("ShowListPerusahaan.zul");
    }

    public void onChange$comboFormCustomer() {
        Executions.sendRedirect("ShowListCustomer.zul");
    }

    public void onClick$buttonSearch() {

        if ((comboInput.getSelectedItem().getValue()) != null && !(comboInput.getSelectedItem().getValue().equals("all"))) {
//        radioInput = radioInput.getSelectedItem().getValue();
            keywords = keywordBox.getValue();
//        radioString = radioInput.getSelectedItem().toString();
//        radioString = radioInput.getAttribute(_applied);
            System.out.println("comboInput = " + comboInput);
            System.out.println("keywords  = " + keywords);
            comboString = comboInput.getSelectedItem().getValue();
            List list = DataAccessObjectPerusahaan.getInstance().searchDataSpecific(comboString, keywords);
            listboxPerusahaan.setItemRenderer(new ShowPerusahaanRenderer());
            listboxPerusahaan.setModel(new ListModelList(list));
        } else if ((comboInput.getSelectedItem().getValue()) != null && (comboInput.getSelectedItem().getValue().equals("all"))) {
            keywords = keywordBox.getValue();
            List list = DataAccessObjectPerusahaan.getInstance().searchDataAll(keywords);
            listboxPerusahaan.setItemRenderer(new ShowPerusahaanRenderer());
            listboxPerusahaan.setModel(new ListModelList(list));
        }
    }

}
