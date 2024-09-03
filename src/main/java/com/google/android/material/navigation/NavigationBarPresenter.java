package com.google.android.material.navigation;
import r.android.content.Context;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
public class NavigationBarPresenter implements MenuPresenter {
  private MenuBuilder menu;
  private NavigationBarMenuView menuView;
  private boolean updateSuspended=false;
  private int id;
  public void setMenuView(  NavigationBarMenuView menuView){
    this.menuView=menuView;
  }
  public void initForMenu(  Context context,  MenuBuilder menu){
    this.menu=menu;
    menuView.initialize(this.menu);
  }
  public void updateMenuView(  boolean cleared){
    if (updateSuspended) {
      return;
    }
    if (cleared) {
      menuView.buildMenuView();
    }
 else {
      menuView.updateMenuView();
    }
  }
  public boolean flagActionItems(){
    return false;
  }
  public void setId(  int id){
    this.id=id;
  }
  public void setUpdateSuspended(  boolean updateSuspended){
    this.updateSuspended=updateSuspended;
  }
}
