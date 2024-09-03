package com.google.android.material.bottomnavigation;
import com.google.android.material.navigation.NavigationBarItemView;
public class BottomNavigationItemView extends NavigationBarItemView {
  protected String getItemLayoutResId(){
    return "@layout/design_bottom_navigation_item_new";
  }
  protected int getItemDefaultMarginResId(){
    return (int) com.ashera.widget.PluginInvoker.convertDpToPixel("6dp");
  }
}
