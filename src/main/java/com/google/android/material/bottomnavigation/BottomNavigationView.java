package com.google.android.material.bottomnavigation;
import r.android.content.Context;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarView;
public class BottomNavigationView extends NavigationBarView {
  static final int MAX_ITEM_COUNT=5;
  public void setItemHorizontalTranslationEnabled(  boolean itemHorizontalTranslationEnabled){
    BottomNavigationMenuView menuView=(BottomNavigationMenuView)getMenuView();
    if (menuView.isItemHorizontalTranslationEnabled() != itemHorizontalTranslationEnabled) {
      menuView.setItemHorizontalTranslationEnabled(itemHorizontalTranslationEnabled);
      getPresenter().updateMenuView(false);
    }
  }
  public boolean isItemHorizontalTranslationEnabled(){
    return ((BottomNavigationMenuView)getMenuView()).isItemHorizontalTranslationEnabled();
  }
  public int getMaxItemCount(){
    return MAX_ITEM_COUNT;
  }
  protected NavigationBarMenuView createNavigationBarMenuView(  Context context){
    String name = BottomNavigationMenuView.class.getName();com.ashera.widget.IWidget widget= com.ashera.widget.WidgetFactory.createWidget(name, name, (com.ashera.widget.HasWidgets) ((com.ashera.widget.ILifeCycleDecorator) this).getWidget(), false);return (BottomNavigationMenuView) widget.asWidget();//new BottomNavigationMenuView();;
  }
}
