package com.bar.barapplication;

import android.widget.Button;
import android.widget.ListView;

public interface AddOrderView {

    ListView getOrderedProductsList();
    Button getOrderButton();
    void destroy();

}
