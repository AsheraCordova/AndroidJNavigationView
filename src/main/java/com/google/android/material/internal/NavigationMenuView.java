package com.google.android.material.internal;
import r.android.content.Context;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
public class NavigationMenuView extends RecyclerView {
  public NavigationMenuView(  Context context){
    setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
  }
  public void initialize(  MenuBuilder menu){
  }
  public int getWindowAnimations(){
    return 0;
  }
}
