/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DataAccessObject;
import VO.customerVO;
import VO.perusahaanVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.directory.SearchControls;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import renderer.ShowCustomerRenderer;

/**
 *
 * @author helloWorld2
 */
public class ShowCustomerController extends GenericForwardComposer<Component> {

    Window windowCustomer;
    Listbox listboxCustomer;
    Combobox comboInput;
    SearchControls search;

    Textbox keywordBox;
    Radiogroup radioInput;
    String radioString;
    String keywords;
    String comboString;
    EventListener<Event> onEnter;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
//    cmbfilter.addEventListener(Event, new EventListener<Event>(){
//    @Override
//    public void onEvent (Event t) throws Exception{
//        prin
//    }
//    });
//comp.addEventListener("onOk", this);
        prepareList();
    }

    public void prepareList() {
        List list = DataAccessObject.getInstance().selectData();
        listboxCustomer.setItemRenderer(new ShowCustomerRenderer());
        listboxCustomer.setModel(new ListModelList(list));
    }

    public void onClick$buttonAdd() {
        Map map = new HashMap();
        map.put("parent", this);
        Executions.createComponents("AddCustomer.zul", null, map);
    }

    public void onClick$buttonUpdate() {
//        Comboitem comboitem =null;
        if (listboxCustomer.getSelectedItem() != null) {
            Object t = listboxCustomer.getSelectedItem().getAttribute("data");
//            String nameCompany = DataAccessObject.getInstance().selectNameCompany(vo.getId_perusahaan());
//        System.out.println("nameCompany =" + nameCompany);

            customerVO vo = (customerVO) t;
//            comboitem.setValue(vo.getId_perusahaan());
//            comboitem.setLabel(vo.getNama_perusahaan());

            System.out.println("vo edit di show = " + vo);
            Map map = new HashMap();
            map.put("obj", vo);
            map.put("parent", this);
            Executions.createComponents("EditCustomer.zul", null, map);

        } else {
            alert("Silahkan pilih data yang ingin di update !");
        }
    }

    public void onClick$buttonDelete() {
        if (listboxCustomer.getSelectedItem() != null) {
            Object t = listboxCustomer.getSelectedItem().getAttribute("data");
            customerVO vo = (customerVO) t;
            DataAccessObject.getInstance().deleteData(vo.getId());
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

//    
//    public void onChange$comboFormCustomer(){
//        System.out.println("masuk");
////        Executions.sendRedirect("ShowListCustomer.zul");
//    }
    public void onChange$comboFormPerusahaan() {
        Executions.sendRedirect("ShowListPerusahaan.zul");
    }

    public void onClick$buttonListPerusahaan() {
        Executions.sendRedirect("ShowListPerusahaan.zul");
    }

    public void onChange$comboFormCustomer() {
        Executions.sendRedirect("ShowListCustomer.zul");
    }
//    public void onChange$comboSearch(){
//        search.equals(keywords);
//    }
//    
//    public void onClick$buttonSearch(){
//        if((radioInput.getSelectedItem().getValue())!= null){
////        radioInput = radioInput.getSelectedItem().getValue();
//        keywords = keywordBox.getValue();
////        radioString = radioInput.getSelectedItem().toString();
////        radioString = radioInput.getAttribute(_applied);
//            System.out.println("radioInput = " +radioInput);
//            System.out.println("keywords  = " +keywords);
//            radioString = radioInput.getSelectedItem().getValue();
//        List list = DataAccessObject.getInstance().searchDataString(radioString, keywords);
//        listboxCustomer.setItemRenderer(new ShowCustomerRenderer());
//        listboxCustomer.setModel(new ListModelList(list));
//        }
//    }

    public void onClick$buttonSearch() {

        if (comboInput.getSelectedItem().getValue() != null && (comboInput.getSelectedItem().getValue().equals("nama_perusahaan"))) {
            keywords = keywordBox.getValue();

            List list = DataAccessObject.getInstance().searchNamaPerusahaanNonVO(keywords);
            System.out.println("ini button search" + list.get(0).toString());

//                customerVO vo = new customerVO();
            List loop = new ArrayList();
            List loop2 = new ArrayList();
            for (int i = 0; i < list.size(); i++) {

                keywords = list.get(i).toString();
                System.out.println("ini keywords =" + keywords);
                comboString = comboInput.getSelectedItem().getValue();
                loop = DataAccessObject.getInstance().searchDataSpecific(comboString, keywords);
                loop2.addAll(loop);
                System.out.println("comboInput.getSelectedItem().getValue() = " + comboInput.getSelectedItem().getValue());
                System.out.println("keywords  = " + keywords);

            }
            listboxCustomer.setItemRenderer(new ShowCustomerRenderer());
            listboxCustomer.setModel(new ListModelList(loop2));

        } else if ((comboInput.getSelectedItem().getValue()) != null && (comboInput.getSelectedItem().getValue().equals("all"))) {
            keywords = keywordBox.getValue();
            List list = DataAccessObject.getInstance().searchDataAll(keywords);
            listboxCustomer.setItemRenderer(new ShowCustomerRenderer());
            listboxCustomer.setModel(new ListModelList(list));
        } else if ((comboInput.getSelectedItem().getValue()) != null && ((!comboInput.getSelectedItem().getValue().equals("all")))) {
            keywords = keywordBox.getValue();
            comboString = comboInput.getSelectedItem().getValue();
            System.out.println("comboInput.getSelectedItem().getValue() = " + comboInput.getSelectedItem().getValue());
            System.out.println("keywords  = " + keywords);
            List list = DataAccessObject.getInstance().searchDataSpecific(comboString, keywords);
            listboxCustomer.setItemRenderer(new ShowCustomerRenderer());
            listboxCustomer.setModel(new ListModelList(list));

        }
    }

    public void onOK$keywordBox(Event event) {
        // do something
    }

    @Listen(Events.ON_OK + " = #keywordBox")
    public void onEnterPressed(Event event) {
        // do something
    }

    @Listen("onQuery = #keywordBox")
    public void onQuery(Event event) {
        // do something
    }
}
