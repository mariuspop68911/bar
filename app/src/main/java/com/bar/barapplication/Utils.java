package com.bar.barapplication;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.bar.barapplication.models.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Utils {

    public boolean equalLists(ArrayList<Order> one, ArrayList<Order> two) {
        if (one == null && two == null) {
            return true;
        }

        if ((one == null && two != null)
                || one != null && two == null
                || one.size() != two.size()) {
            return false;
        }

        one = new ArrayList<>(one);
        two = new ArrayList<>(two);

        Comparator<Order> comp = new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o2.getOrderNumber() - o1.getOrderNumber();
            }
        };

        Collections.sort(one, comp);
        Collections.sort(two, comp);

        for (int i = 0; i < one.size(); i++) {
            if (two.get(i).getOrderId() != one.get(i).getOrderId()) {
                return false;
            }
            if (two.get(i).getStatus() != one.get(i).getStatus()) {
                return false;
            }
            if(two.get(i).getProductIds() != null) {
                for (int j = 0; j < two.get(i).getProductIds().length; j++) {
                    if (!two.get(i).getProductIds()[j].equals(one.get(i).getProductIds()[j])) {
                        return false;
                    }
                }
            }
            if (two.get(i).getClientName() != null && !two.get(i).getClientName().equals(one.get(i).getClientName())) {
                return false;
            }
            if (two.get(i).getOrderNumber() != one.get(i).getOrderNumber()) {
                return false;
            }

        }

        return true;
    }

    public void playSound(Context context) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
