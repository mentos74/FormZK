/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import VO.customerVO;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ListModelList;

/**
 *
 * @author helloWorld2
 */
public class UIUtil {

    public static void renderCombobox(Combobox combobox, List list, ComboitemRenderer comboitemRenderer) {
        try {

            if (!(list != null && list.size() > 0)) {
                list = new ArrayList();
            }
            combobox.setModel(new ListModelList(list));
            combobox.setItemRenderer(comboitemRenderer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getComboValue(Combobox combobox) {
        String temp = null;
        try {
            if (combobox != null) {

                Comboitem comboitem = combobox.getSelectedItem();

                temp = comboitem.getValue().toString();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

}
