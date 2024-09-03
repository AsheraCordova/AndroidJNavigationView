package com.google.android.material.navigation;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import r.android.view.MenuItem;
public final class NavigationBarMenu extends MenuBuilder {
  private final Class<?> viewClass;
  private final int maxItemCount;
  public NavigationBarMenu(     Class<?> viewClass,  int maxItemCount){
    //super(context);
    this.viewClass=viewClass;
    this.maxItemCount=maxItemCount;
  }
  public int getMaxItemCount(){
    return maxItemCount;
  }
  protected MenuItem addInternal(  int group,  int id,  int categoryOrder,  CharSequence title){
    if (size() + 1 > maxItemCount) {
      String viewClassName=viewClass.getSimpleName();
      throw new IllegalArgumentException("Maximum number of items supported by " + viewClassName + " is "+ maxItemCount+ ". Limit can be checked with "+ viewClassName+ "#getMaxItemCount()");
    }
    stopDispatchingItemsChanged();
    final MenuItem item=super.addInternal(group,id,categoryOrder,title);
    if (item instanceof MenuItemImpl) {
      ((MenuItemImpl)item).setExclusiveCheckable(true);
    }
    startDispatchingItemsChanged();
    return item;
  }
}
